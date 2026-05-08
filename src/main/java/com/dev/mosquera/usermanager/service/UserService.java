package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> findAllUsers();
    UserResponse findById(Long id);
    List<UserResponse> findUsersOlderThan(int age);
}
