package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class IntroduceInfoDto {
    private UUID uuid;
    private String title;
    private String subtitle;
    private String description;
    private String imageBase64;
}
