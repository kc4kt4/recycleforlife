package com.recycleforlife.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class UserDto {
    private UUID uuid;
    private String login;
    private String name;
    private Integer age;
    private Sex sex;
}
