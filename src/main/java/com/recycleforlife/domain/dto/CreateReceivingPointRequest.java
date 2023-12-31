package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CreateReceivingPointRequest {
    @NotNull
    private UUID uuid;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;

    private String subtitle;
    private String email;
    private String msisdn;
    private List<WorkingHour> workingHours = new ArrayList<>();
}
