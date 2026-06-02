package com.dev.mosquera.usermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
