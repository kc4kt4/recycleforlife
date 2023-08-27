package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.CategoryDto;
import com.recycleforlife.domain.model.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

class CategoryDtoMapperTest {
    final CategoryDtoMapper mapper = Mappers.getMapper(CategoryDtoMapper.class);

    @Test
    void toDto() {
        final Category category = new Category()
                .setId(1L)
                .setUuid(UUID.randomUUID())
                .setParentId(123L)
                .setName("n")
                .setDescription("d");
        final UUID subUuid = UUID.randomUUID();
        final UUID parentUuid = UUID.randomUUID();

        //test
        final CategoryDto result = mapper.toDto(category, List.of(subUuid), parentUuid);

        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getParentUuid().equals(parentUuid))
                .matches(e -> e.getSubCategoryUuids().size() == 1)
                .matches(e -> e.getSubCategoryUuids().get(0).equals(subUuid))
                .usingRecursiveComparison()
                .ignoringFields("parentUuid", "subCategoryUuids")
                .isEqualTo(category);
    }
}