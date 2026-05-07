package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.exception.UserNotFoundException;
import com.dev.mosquera.usermanager.model.User;
import com.dev.mosquera.usermanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if(userExists(userRequest.getId())) {
            throw new IllegalArgumentException("The user with id " + userRequest.getId() + " is already created");
        }
        return mapToResponse(repository.save(mapToUser(userRequest)));
    }

    public List<UserResponse> findAllUsers() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public UserResponse findById(Long id) {
        if(id == null) throw new IllegalArgumentException("The id cannot be null");
        return repository.findById(id).map(this::mapToResponse).orElseThrow(() -> new UserNotFoundException("The user with id " + id + " was not found!"));
    }

    public List<UserResponse> findUsersOlderThan(int age) {
        if(age < 0) throw new IllegalArgumentException("The age should be 0 or greater");
        return repository.findByAgeGreaterThan(age).stream().map(this::mapToResponse).toList();
    }

    private boolean userExists(Long id) {
        return repository.existsById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder().id(user.getId()).name(user.getName()).lastname(user.getLastname()).age(user.getAge()).build();
    }

    private User mapToUser(UserRequest request) {
        return User.builder().id(request.getId()).name(request.getName()).lastname(request.getLastname()).age(request.getAge()).build();
    }
}
