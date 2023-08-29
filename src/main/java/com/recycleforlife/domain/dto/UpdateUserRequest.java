package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UpdateUserRequest {
    @NotBlank
    private String name;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private Sex sex;
}
