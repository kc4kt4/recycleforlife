package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class NewsDto extends SimpleNewsDto {
    @NotBlank
    private String description;
    @NotBlank
    private String title;
}
