package com.recycleforlife.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryDto {
    private UUID uuid;
    private UUID parentUuid;
    private String name;
    private String description;
    private List<UUID> subCategoryUuids;
}
