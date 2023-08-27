package com.recycleforlife.domain.dto;

import lombok.Data;

@Data
public class AuthorizationHolder {
    private UserDto userDto;
    private String jwt;
    private String refreshToken;
}
