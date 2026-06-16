package com.dev.mosquera.usermanager.service.implementation;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.exception.UserAlreadyExistsException;
import com.dev.mosquera.usermanager.exception.UserNotFoundException;
import com.dev.mosquera.usermanager.mapper.UserMapper;
import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.model.User;
import com.dev.mosquera.usermanager.repository.UserRepository;
import com.dev.mosquera.usermanager.service.UserService;
import com.dev.mosquera.usermanager.service.notification.DefaultRoleNotificationManager;
import com.dev.mosquera.usermanager.service.notification.NotificationService;
import com.dev.mosquera.usermanager.service.notification.RoleNotificationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final NotificationService notificationService;
    private final RoleNotificationManager manager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.mapToUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCreated = repository.save(user);
        NotificationType userNotificationType = manager.resolve(userCreated.getRole());
        notificationService.send(userNotificationType, "Welcome " + userCreated.getName() + " " + userCreated.getLastname());
        return mapper.mapToResponse(userCreated);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return repository.findAll().stream().map(mapper::mapToResponse).toList();
    }

    @Override
    public UserResponse findById(Long id) {
        if(id == null) throw new IllegalArgumentException("The id cannot be null");
        return repository.findById(id).map(mapper::mapToResponse).orElseThrow(() -> new UserNotFoundException("The user with id " + id + " was not found!"));
    }

    @Override
    public List<UserResponse> findUsersOlderThan(int age) {
        if(age < 0) throw new IllegalArgumentException("The age should be 0 or greater");
        return repository.findByAgeGreaterThan(age).stream().map(mapper::mapToResponse).toList();
    }
}
