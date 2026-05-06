package com.dev.mosquera.usermanager.controller;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.model.User;
import com.dev.mosquera.usermanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestBody.getId(), requestBody.getName(), requestBody.getLastname(), requestBody.getAge()));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping(params = "age")
    public ResponseEntity<List<UserResponse>> findUsersOlderThanAge(@RequestParam int age) {
        return ResponseEntity.ok().body(userService.findUsersOlderThan(age));
    }
}
