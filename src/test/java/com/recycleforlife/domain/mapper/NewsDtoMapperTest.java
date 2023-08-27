package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.NewsDto;
import com.recycleforlife.domain.dto.SimpleNewsDto;
import com.recycleforlife.domain.model.News;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

class NewsDtoMapperTest {
    final NewsDtoMapper mapper = Mappers.getMapper(NewsDtoMapper.class);

    @Test
    void toDto() {
        final News news = new News()
                .setId(1L)
                .setUuid(UUID.randomUUID())
                .setName("n")
                .setShortDescription("sd")
                .setDescription("d")
                .setImageBase64("i")
                .setDate(LocalDate.now());

        //test
        final NewsDto result = mapper.toDto(news);

        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(news);
    }

    @Test
    void toSimpleDto() {
        final News news = new News()
                .setId(1L)
                .setUuid(UUID.randomUUID())
                .setName("n")
                .setShortDescription("sd")
                .setDescription("d")
                .setImageBase64("i")
                .setDate(LocalDate.now());

        //test
        final SimpleNewsDto result = mapper.toSimpleDto(news);

        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(news);
    }

    @Test
    void toInner() {
        final NewsDto dto = new NewsDto();
        dto
                .setUuid(UUID.randomUUID())
                .setName("n")
                .setShortDescription("sd")
                .setImageBase64("i")
                .setDate(LocalDate.now());

        //test
        final News result = mapper.toInner(dto);

        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }
}
