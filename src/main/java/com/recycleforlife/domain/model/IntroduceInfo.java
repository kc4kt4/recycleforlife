package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class IntroduceInfo {
    private @Id Long id;
    private UUID uuid;
    private String title;
    private String subtitle;
    private String description;
    private String imageBase64;
}
