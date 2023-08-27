package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.CreateReceivingPointRequest;
import com.recycleforlife.domain.dto.ReceivingPointDto;
import com.recycleforlife.domain.dto.UpdateUserRequest;
import com.recycleforlife.domain.model.ReceivingPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReceivingPointMapperTest {
    final ReceivingPointMapper mapper = Mappers.getMapper(ReceivingPointMapper.class);

    @Test
    void toDto() {
        final ReceivingPoint model = new ReceivingPoint()
                .setId(1L)
                .setUuid(UUID.randomUUID())
                .setName("n")
                .setDescription("d")
                .setLongitude(new BigDecimal("1.1"))
                .setLatitude(new BigDecimal("1.2"));
        final List<UUID> uuids = List.of(UUID.randomUUID());

        //test
        final ReceivingPointDto result = mapper.toDto(model, uuids);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getFractionUuids().equals(uuids))
                .usingRecursiveComparison()
                .ignoringFields("fractionUuids")
                .isEqualTo(model);
    }

    @Test
    void toModel() {
        final CreateReceivingPointRequest request = new CreateReceivingPointRequest()
                .setUuid(UUID.randomUUID())
                .setName("n")
                .setDescription("d")
                .setLongitude(new BigDecimal("1.1"))
                .setLatitude(new BigDecimal("1.2"));

        //test
        final ReceivingPoint result = mapper.toModel(request);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getId() == null)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(request);
    }
}
