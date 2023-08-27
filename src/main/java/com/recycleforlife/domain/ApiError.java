package com.recycleforlife.domain;

import lombok.Data;

@Data
public class ApiError {
    private ErrorCode code;
    private String message;
}
