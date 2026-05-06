package com.dev.mosquera.usermanager.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
public class User {
    private final Long id;
    private final String name;
    private final String lastname;
    private final int age;

    public User(Long id, String name, String lastname, int age) {
        if(id == null) throw new IllegalArgumentException("Id cannot be null");
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if(lastname == null || lastname.isBlank()) throw new IllegalArgumentException("Lastname is required");
        if(age < 0) throw new IllegalArgumentException("Age cannot be negative");

        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }
}
