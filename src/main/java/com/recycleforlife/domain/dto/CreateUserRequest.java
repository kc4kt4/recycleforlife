package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateUserRequest {
    @NotNull
    private UUID uuid;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private Sex sex;
}
