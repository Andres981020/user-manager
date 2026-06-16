package com.dev.mosquera.usermanager.dto;

import com.dev.mosquera.usermanager.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private int age;
    private UserRole role;
}
