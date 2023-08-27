package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class News {
    private @Id Long id;
    private UUID uuid;
    private String name;
    private String shortDescription;
    private String description;
    private String title;
    private String imageBase64;
    private LocalDate date;
}
