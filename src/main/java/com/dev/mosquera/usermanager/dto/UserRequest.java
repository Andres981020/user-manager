package com.dev.mosquera.usermanager.dto;

import com.dev.mosquera.usermanager.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @PositiveOrZero
    private int age;

    private UserRole role;
}
