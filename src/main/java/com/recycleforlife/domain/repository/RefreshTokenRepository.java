package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.RefreshToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(@NotNull String refreshToken);

    List<RefreshToken> findAllByuserIdAndDeactivatedAtIsNull(long userId);
}
