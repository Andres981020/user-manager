package com.dev.mosquera.usermanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private int age;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
