package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class FractionReceivingPoint {
    private @Id Long id;
    private Long receivingPointId;
    private Long fractionId;
}
