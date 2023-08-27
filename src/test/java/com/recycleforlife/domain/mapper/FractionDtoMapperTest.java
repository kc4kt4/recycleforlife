package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.FractionDto;
import com.recycleforlife.domain.model.Fraction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

class FractionDtoMapperTest {
    final FractionDtoMapper mapper = Mappers.getMapper(FractionDtoMapper.class);

    @Test
    void toDto() {
        final Fraction fraction = new Fraction()
                .setId(1L)
                .setUuid(UUID.randomUUID())
                .setCategoryId(2L)
                .setName("n")
                .setDescription("d")
                .setArticle("a")
                .setImageBase64("i");
        final UUID uuid = UUID.randomUUID();
        final List<UUID> uuids = List.of(UUID.randomUUID());

        //test
        final FractionDto result = mapper.toDto(fraction, uuid, uuids);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getCategoryUuid().equals(uuid))
                .matches(e -> e.getReceivingPointUuids().equals(uuids))
                .usingRecursiveComparison()
                .ignoringFields("categoryUuid", "receivingPointUuids")
                .isEqualTo(fraction);
    }

    @Test
    void toInner() {
        final FractionDto dto = new FractionDto()
                .setUuid(UUID.randomUUID())
                .setCategoryUuid(UUID.randomUUID())
                .setName("n")
                .setDescription("d")
                .setArticle("a")
                .setTitle("t")
                .setImageBase64("i");

        //test
        final Fraction result = mapper.toInner(dto, 1L);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getCategoryId() == 1L)
                .usingRecursiveComparison()
                .ignoringFields("id", "categoryId")
                .isEqualTo(dto);
    }
}
