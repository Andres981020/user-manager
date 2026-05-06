package com.dev.mosquera.usermanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UserRequest {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastname;

    @PositiveOrZero
    private int age;
}
