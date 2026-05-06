package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.exception.UserNotFoundException;
import com.dev.mosquera.usermanager.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.addAll(List.of(
                new User(1L, "Carlos", "Perez", 12),
                new User(2L, "Pepito", "Perez", 15)
        ));
    }
    public UserResponse createUser(Long id, String name, String lastname, int age) {
        if(userExists(id)) {
            throw new IllegalArgumentException("The user with id " + id + " is already created");
        }

        User newUser = new User(id, name, lastname, age);
        UserResponse response = userMapToUserResponse(newUser);
        users.add(newUser);
        return response;
    }

    public List<UserResponse> findAllUsers() {
        return users.stream().map(this::userMapToUserResponse).toList();
    }

    public UserResponse findById(Long id) {
        if(id == null) throw new IllegalArgumentException("The id cannot be null");
        return users.stream().filter(u -> Objects.equals(u.getId(), id)).map(this::userMapToUserResponse).findFirst().orElseThrow(() -> new UserNotFoundException("The user was not found!"));
    }

    public List<UserResponse> findUsersOlderThan(int age) {
        if(age < 0) throw new IllegalArgumentException("Age cannot be negative");
        return users.stream().filter(u -> u.getAge() > age).map(this::userMapToUserResponse).toList();
    }

    private boolean userExists(Long id) {
        if(id == null) throw new IllegalArgumentException("The Id cannot be null");
        return users.stream().anyMatch(u -> Objects.equals(u.getId(), id));
    }

    private UserResponse userMapToUserResponse(User user) {
        return UserResponse.builder().id(user.getId()).name(user.getName()).lastname(user.getLastname()).age(user.getAge()).build();
    }
}
