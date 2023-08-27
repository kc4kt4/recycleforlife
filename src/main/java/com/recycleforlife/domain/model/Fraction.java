package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class Fraction {
    private @Id Long id;
    private UUID uuid;
    private Long categoryId;
    private String name;
    private String description;
    private String title;
    private String article;
    private String imageBase64;
}
