package com.recycleforlife.domain.repository;

import com.recycleforlife.domain.model.ReceivingPoint;
import com.recycleforlife.domain.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReceivingPointRepository extends CrudRepository<ReceivingPoint, Long> {

    @Override
    @NotNull
    List<ReceivingPoint> findAll();

    Optional<ReceivingPoint> findByUuid(@NotNull UUID uuid);

    @Query(
            """
            select rp.uuid from fraction_receiving_point frp
            join receiving_point rp on rp.id = frp.receiving_point_id
            where frp.fraction_id = :fractionId
            """
    )
    List<UUID> findPointsUuidForFractionId(long fractionId);
}
