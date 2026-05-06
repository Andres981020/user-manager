package com.dev.mosquera.usermanager.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String name;
    private String lastname;
    private int age;
}
