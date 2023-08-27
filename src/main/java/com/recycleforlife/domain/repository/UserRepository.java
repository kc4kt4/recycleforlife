package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(@NotNull String login);

    Optional<User> findByUuid(@NotNull UUID uuid);
}
