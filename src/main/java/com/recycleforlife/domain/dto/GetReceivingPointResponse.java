package com.recycleforlife.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetReceivingPointResponse {
    private List<ReceivingPointDto> receivingPoints;
}
