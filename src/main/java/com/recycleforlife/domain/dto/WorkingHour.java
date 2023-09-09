package com.recycleforlife.domain.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class WorkingHour {
    private Integer dayOfWeek;
    private LocalTime from;
    private LocalTime to;
}
