package com.recycleforlife.domain.model;

import com.recycleforlife.domain.dto.Sex;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
    private @Id Long id;
    private UUID uuid;
    private String login;
    private String encodedPassword;
    private String name;
    private LocalDate dateOfBirth;
    private Sex sex;
}
