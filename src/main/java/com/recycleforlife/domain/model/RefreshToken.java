package com.recycleforlife.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class RefreshToken {
    private @Id Long id;
    private Long userId;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime deactivatedAt;
}
