package com.dev.mosquera.usermanager.dto;

import lombok.Builder;

@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String lastname;
    private int age;
}
