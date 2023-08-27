package com.recycleforlife.domain.mapper;

import com.recycleforlife.domain.dto.CategoryDto;
import com.recycleforlife.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CategoryDtoMapper {

    @Mapping(target = "subCategoryUuids", source = "subCategoryUuids")
    @Mapping(target = "parentUuid", source = "parentUuid")
    CategoryDto toDto(Category category, List<UUID> subCategoryUuids, UUID parentUuid);
}
