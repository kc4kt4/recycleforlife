package com.recycleforlife.domain.model;

import com.recycleforlife.domain.dto.WorkingHour;
import lombok.Data;

import java.util.List;

@Data
public class WorkingHours {
    private List<WorkingHour> workingHours;
}
