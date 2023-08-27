package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FractionDto {
    @NotNull
    private UUID uuid;
    @NotNull
    private UUID categoryUuid;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String title;
    @NotBlank
    private String article;
    private String imageBase64;
    private List<UUID> receivingPointUuids;
}
