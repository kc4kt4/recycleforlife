package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateCategoryRequest {
    @NotNull
    private UUID uuid;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
