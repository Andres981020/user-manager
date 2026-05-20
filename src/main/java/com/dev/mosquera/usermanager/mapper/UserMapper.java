package com.dev.mosquera.usermanager.mapper;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse mapToResponse(User user) {
        return UserResponse.builder().id(user.getId()).name(user.getName()).lastname(user.getLastname()).age(user.getAge()).role(user.getRole()).build();
    }

    public User mapToUser(UserRequest request) {
        return User.builder().id(request.getId()).name(request.getName()).lastname(request.getLastname()).age(request.getAge()).role(request.getRole()).build();
    }
}
