package com.recycleforlife.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SubCategoryRequest {
    private List<UUID> subCategoryUuids = new ArrayList<>();
}
