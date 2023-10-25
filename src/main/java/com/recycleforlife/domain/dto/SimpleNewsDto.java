package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SimpleNewsDto {
    @NotNull
    private UUID uuid;
    @NotBlank
    private String name;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String imageBase64;
    @NotNull
    private LocalDate date;
    @NotBlank
    private String city;
    @NotBlank
    private NewsType type;
}
