package com.recycleforlife.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID uuid;
    private String login;
    private String name;
    private LocalDate dateOfBirth;
    private Sex sex;
}
