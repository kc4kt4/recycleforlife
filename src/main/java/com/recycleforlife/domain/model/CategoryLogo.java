package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class CategoryLogo {
    private @Id Long id;
    private UUID uuid;
    private Long categoryId;
    private String imageBase64;
}
