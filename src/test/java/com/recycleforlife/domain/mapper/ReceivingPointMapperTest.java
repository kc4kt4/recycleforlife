package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.CreateReceivingPointRequest;
import com.recycleforlife.domain.dto.ReceivingPointDto;
import com.recycleforlife.domain.dto.UpdateUserRequest;
import com.recycleforlife.domain.dto.WorkingHour;
import com.recycleforlife.domain.model.ReceivingPoint;
import com.recycleforlife.domain.model.WorkingHours;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalTime;
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
                .setLatitude(new BigDecimal("1.2"))
                .setSubtitle("ss")
                .setEmail("eee")
                .setMsisdn("888")
                .setWorkingHours(
                        new WorkingHours()
                                .setWorkingHours(List.of(
                                        new WorkingHour()
                                                .setTo(LocalTime.of(10, 10, 10))
                                                .setFrom(LocalTime.of(10, 10, 11))
                                                .setDayOfWeek(1)
                                ))
                );
        final List<UUID> uuids = List.of(UUID.randomUUID());

        //test
        final ReceivingPointDto result = mapper.toDto(model, uuids);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getFractionUuids().equals(uuids))
                .matches(e -> e.getWorkingHours().size() == 1)
                .matches(e -> e.getWorkingHours().get(0).equals(model.getWorkingHours().getWorkingHours().get(0)))
                .usingRecursiveComparison()
                .ignoringFields("fractionUuids", "workingHours")
                .isEqualTo(model);
    }

    @Test
    void toModel() {
        final CreateReceivingPointRequest request = new CreateReceivingPointRequest()
                .setUuid(UUID.randomUUID())
                .setName("n")
                .setDescription("d")
                .setLongitude(new BigDecimal("1.1"))
                .setLatitude(new BigDecimal("1.2"))
                .setSubtitle("ss")
                .setEmail("eee")
                .setMsisdn("888")
                .setWorkingHours(
                        List.of(
                                new WorkingHour()
                                        .setTo(LocalTime.of(10, 10, 10))
                                        .setFrom(LocalTime.of(10, 10, 11))
                                        .setDayOfWeek(1)
                        )
                );

        //test
        final ReceivingPoint result = mapper.toModel(request);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getId() == null)
                .matches(e -> e.getWorkingHours() != null)
                .matches(e -> e.getWorkingHours().getWorkingHours() != null)
                .matches(e -> e.getWorkingHours().getWorkingHours().size() == 1)
                .matches(e -> e.getWorkingHours().getWorkingHours().get(0).equals(request.getWorkingHours().get(0)))
                .usingRecursiveComparison()
                .ignoringFields("id", "workingHours")
                .isEqualTo(request);
    }
}
