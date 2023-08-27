package com.recycleforlife.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtPayloadDto {
    private UUID userUuid;
}
