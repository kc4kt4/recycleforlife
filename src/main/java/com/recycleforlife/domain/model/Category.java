package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class Category {
    private @Id Long id;
    private Long parentId;
    private UUID uuid;
    private String name;
    private String description;
}
