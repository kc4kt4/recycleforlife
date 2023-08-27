package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query(
            """
            select * from category c
            where c.parent_id is null
            """
    )
    List<Category> findRoots();

    List<Category> findByParentId(long parentId);

    Optional<Category> findByUuid(@NotNull UUID uuid);
}
