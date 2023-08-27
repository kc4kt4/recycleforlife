package com.recycleforlife.domain.exception;

import com.recycleforlife.domain.ApiError;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ForbiddenApiException extends RuntimeException {
    private final ApiError apiError;

    public ForbiddenApiException(final @NotNull ApiError apiError) {
        super(apiError.getMessage());
        this.apiError = apiError;
    }
}
