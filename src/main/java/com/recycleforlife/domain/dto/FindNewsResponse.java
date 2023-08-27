package com.recycleforlife.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FindNewsResponse {
    private List<SimpleNewsDto> news;
}
