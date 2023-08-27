package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.News;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsRepository extends CrudRepository<News, Long> {

    Optional<News> findByUuid(@NotNull UUID uuid);

    @Query(
            """
            select * from news n
            where n.date >= :from and n.date < :to
            order by date
            """
    )
    List<News> findByPeriod(@NotNull LocalDate from, @NotNull LocalDate to);
}
