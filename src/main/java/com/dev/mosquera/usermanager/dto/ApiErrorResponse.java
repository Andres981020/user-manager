package com.dev.mosquera.usermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
