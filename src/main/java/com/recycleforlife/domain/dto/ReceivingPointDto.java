package com.recycleforlife.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ReceivingPointDto {
    private UUID uuid;
    private String name;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<UUID> fractionUuids;
}
