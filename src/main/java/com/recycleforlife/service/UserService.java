package com.recycleforlife.service;

import com.recycleforlife.domain.ApiError;
import com.recycleforlife.domain.ErrorCode;
import com.recycleforlife.domain.dto.AuthorizationHolder;
import com.recycleforlife.domain.dto.AuthorizeRequest;
import com.recycleforlife.domain.dto.CreateUserRequest;
import com.recycleforlife.domain.dto.UpdateUserRequest;
import com.recycleforlife.domain.dto.UserDto;
import com.recycleforlife.domain.exception.ApiException;
import com.recycleforlife.domain.exception.ForbiddenApiException;
import com.recycleforlife.domain.exception.UnauthorizedApiException;
import com.recycleforlife.domain.mapper.UserMapper;
import com.recycleforlife.domain.model.RefreshToken;
import com.recycleforlife.domain.model.User;
import com.recycleforlife.domain.repository.RefreshTokenRepository;
import com.recycleforlife.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final DateService dateService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    @NotNull
    public UserDto findUser(final @NotNull UUID uuid) {
        return userRepository.findByUuid(uuid)
                .map(userMapper::toDto)
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.USER_NOT_FOUND)
                                    .setMessage("user not found")
                    );
                });
    }

    @NotNull
    public UserDto updateUser(final UUID uuid, final UpdateUserRequest updateUserRequest) {
        final User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.USER_NOT_FOUND)
                                    .setMessage("user not found")
                    );
                });

        final User forUpdate = user.setSex(updateUserRequest.getSex())
                .setName(updateUserRequest.getName())
                .setDateOfBirth(updateUserRequest.getDateOfBirth());
        final User updated = userRepository.save(forUpdate);
        log.info("user updated: {}", updated);

        return userMapper.toDto(updated);
    }

    @NotNull
    public AuthorizationHolder createUser(final @NotNull CreateUserRequest request) {
        final Optional<User> existingUserByLogin = userRepository.findByLogin(request.getLogin());

        if (existingUserByLogin.isPresent()) {
            throw new ApiException(
                    new ApiError()
                            .setCode(ErrorCode.LOGIN_EXISTS)
                            .setMessage("login already exists")
            );
        }

        final User user = new User()
                .setUuid(request.getUuid())
                .setLogin(request.getLogin())
                .setName(request.getName())
                .setEncodedPassword(passwordEncoder.encode(request.getPassword()))
                .setDateOfBirth(request.getDateOfBirth())
                .setSex(request.getSex());
        final User saved = userRepository.save(user);
        log.info("user saved - {}", saved);

        final Instant now = dateService.instantNow();

        final String jwt = generateJwt(user.getUuid(), now);
        final String rt = generateRefreshToken(user.getUuid());

        createRefreshToken(user, dateService.timeNow(), rt);

        return new AuthorizationHolder()
                .setJwt(jwt)
                .setRefreshToken(rt)
                .setUserDto(userMapper.toDto(user));
    }

    @NotNull
    public AuthorizationHolder refresh(final @NotNull String rt) {
        final RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(rt)
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.REFRESH_TOKEN_NOT_FOUND)
                                    .setMessage("refresh token not found")
                    );
                });

        final LocalDateTime now = dateService.timeNow();
        if (refreshToken.getExpiredAt().isBefore(now)) {
            throw new ApiException(
                    new ApiError()
                            .setCode(ErrorCode.REFRESH_TOKEN_NOT_FOUND)
                            .setMessage("refresh token expired")
            );
        }

        if (refreshToken.getDeactivatedAt() != null) {
            throw new ApiException(
                    new ApiError()
                            .setCode(ErrorCode.REFRESH_TOKEN_NOT_FOUND)
                            .setMessage("refresh token refreshed")
            );
        }

        final RefreshToken forUpdate = refreshToken.setDeactivatedAt(now);
        final RefreshToken updated = refreshTokenRepository.save(forUpdate);
        log.info("refreshed - {}", updated);

        final User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.USER_NOT_FOUND)
                                    .setMessage("user not found")
                    );
                });

        final Instant instantNow = dateService.instantNow();

        final String jwt = generateJwt(user.getUuid(), instantNow);
        final String newRt = generateRefreshToken(user.getUuid());

        createRefreshToken(user, now, newRt);

        return new AuthorizationHolder()
                .setJwt(jwt)
                .setRefreshToken(newRt)
                .setUserDto(userMapper.toDto(user));
    }

    public void logout(final String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken)
                .ifPresent(e -> {
                    if (e.getDeactivatedAt() != null) {
                        final RefreshToken rt = e.setDeactivatedAt(dateService.timeNow());
                        final RefreshToken deactivated = refreshTokenRepository.save(rt);
                        log.info("deactivated refresh token - {}", deactivated);
                    }
                });
    }

    @NotNull
    public AuthorizationHolder authorize(final AuthorizeRequest request) {
        final User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> {
                    throw new ApiException(
                            new ApiError()
                                    .setCode(ErrorCode.USER_NOT_FOUND)
                                    .setMessage("user not found")
                    );
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getEncodedPassword())) {
            log.info("passwords do not match");
            throw new ForbiddenApiException(
                    new ApiError()
                            .setCode(ErrorCode.USER_NOT_FOUND)
                            .setMessage("passwords do not match")
            );
        }

        final Instant instantNow = dateService.instantNow();
        final String jwt = generateJwt(user.getUuid(), instantNow);
        final String rt = generateRefreshToken(user.getUuid());

        final LocalDateTime now = dateService.timeNow();
        refreshTokenRepository.findAllByuserIdAndDeactivatedAtIsNull(user.getId())
                .forEach(e -> {
                    final RefreshToken refreshToken = e.setDeactivatedAt(now);
                    final RefreshToken deactivated = refreshTokenRepository.save(refreshToken);
                    log.info("refresh deactivated - {}", deactivated);
                });

        createRefreshToken(user, now, rt);

        return new AuthorizationHolder()
                .setJwt(jwt)
                .setRefreshToken(rt)
                .setUserDto(userMapper.toDto(user));
    }

    public void checkJwt(final @NotNull String jwt) {
        try {
            final Claims body = Jwts.parser()
                    .setSigningKey("secret".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt).getBody();
        } catch (final ExpiredJwtException e) {
            throw new UnauthorizedApiException(
                    new ApiError()
                            .setCode(ErrorCode.TOKEN_EXPIRED)
                            .setMessage("token expired")
            );
        }
    }

    @NotNull
    private String generateJwt(final @NotNull UUID userUuid, final @NotNull Instant now) {
        final Claims claims = Jwts.claims()
                .setIssuer(userUuid.toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(300L)))
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    @NotNull
    private String generateRefreshToken(final @NotNull UUID userUuid) {
        return DigestUtils.sha256Hex(userUuid + "salt" + dateService.instantNow().toEpochMilli());
    }

    private void createRefreshToken(final @NotNull User user, final @NotNull LocalDateTime now, final @NotNull String rt) {
        final RefreshToken refreshToken = new RefreshToken()
                .setUserId(user.getId())
                .setRefreshToken(rt)
                .setCreatedAt(now)
                .setExpiredAt(now.plusYears(1));
        final RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
        log.info("saved refresh token - {}", savedRefreshToken);
    }
}
