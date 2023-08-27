package com.recycleforlife.domain.model;

import com.recycleforlife.domain.dto.Sex;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class User {
    private @Id Long id;
    private UUID uuid;
    private String login;
    private String encodedPassword;
    private String name;
    private Integer age;
    private Sex sex;
}
