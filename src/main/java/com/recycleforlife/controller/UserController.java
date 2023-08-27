package com.recycleforlife.controller;

import com.recycleforlife.domain.dto.AuthorizationHolder;
import com.recycleforlife.domain.dto.AuthorizeRequest;
import com.recycleforlife.domain.dto.CreateUserRequest;
import com.recycleforlife.domain.dto.UpdateUserRequest;
import com.recycleforlife.domain.dto.UserDto;
import com.recycleforlife.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping
public class UserController {
    public static final String JWT = "X-Token";
    public static final String REFRESH_TOKEN = "X-Refresh-Token";

    private final UserService service;

    @PostMapping(
            path = "/public/v1/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto createUser(final @Valid @RequestBody CreateUserRequest request, final HttpServletResponse httpServletResponse) {
        final AuthorizationHolder authorizationHolder = service.createUser(request);
        httpServletResponse.addHeader(JWT, authorizationHolder.getJwt());
        httpServletResponse.addHeader(REFRESH_TOKEN, authorizationHolder.getRefreshToken());

        return authorizationHolder.getUserDto();
    }

    @GetMapping(
            path = "/public/v1/users/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto createUser(final @PathVariable UUID uuid) {
        return service.findUser(uuid);
    }

    @PutMapping(
            path = "/public/v1/users/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto createUser(final @PathVariable UUID uuid, final @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return service.updateUser(uuid, updateUserRequest);
    }

    @PostMapping(
            path = "/public/v1/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto authorize(final @Valid @RequestBody AuthorizeRequest request, final HttpServletResponse httpServletResponse) {
        final AuthorizationHolder authorizationHolder = service.authorize(request);
        httpServletResponse.addHeader(JWT, authorizationHolder.getJwt());
        httpServletResponse.addHeader(REFRESH_TOKEN, authorizationHolder.getRefreshToken());

        return authorizationHolder.getUserDto();
    }

    @PostMapping(path = "/public/v1/refresh")
    public void refresh(final @RequestHeader(REFRESH_TOKEN) String refreshToken, final HttpServletResponse httpServletResponse) {
        final AuthorizationHolder authorizationHolder = service.refresh(refreshToken);
        httpServletResponse.addHeader(JWT, authorizationHolder.getJwt());
        httpServletResponse.addHeader(REFRESH_TOKEN, authorizationHolder.getRefreshToken());
    }

    @PostMapping(path = "/public/v1/logout")
    public void logout(final @RequestHeader(REFRESH_TOKEN) String refreshToken) {
        service.logout(refreshToken);
    }
}
