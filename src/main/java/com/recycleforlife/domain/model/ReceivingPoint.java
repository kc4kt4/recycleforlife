package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ReceivingPoint {
    private @Id Long id;
    private UUID uuid;
    private String name;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
