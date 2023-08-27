package com.recycleforlife.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorizeRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
