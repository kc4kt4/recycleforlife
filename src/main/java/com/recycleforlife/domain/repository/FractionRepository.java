package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.Fraction;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FractionRepository extends CrudRepository<Fraction, Long> {

    @Query(
            """
            select f.* from fraction f
            join category c on f.category_id = c.id
            where c.uuid = :categoryUuid
            """
    )
    List<Fraction> findForCategory(@NotNull UUID categoryUuid);

    Optional<Fraction> findByUuid(@NotNull UUID uuid);

    @Override
    @NotNull
    List<Fraction> findAll();

    @Query(
            """
            select f.uuid from fraction_receiving_point frp
            join fraction f on f.id = frp.fraction_id
            where frp.receiving_point_id = :receivingPointId
            """
    )
    @NotNull
    List<UUID> findAllFractionsByReceivingPointId(long receivingPointId);
}
