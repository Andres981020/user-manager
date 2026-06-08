package com.dev.mosquera.usermanager.controller;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestBody));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @Operation(
            summary = "Find user by id",
            description = "Returns a user by its id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(
            @Parameter(
                    name = "id",
                    description = "Id of the user we are looking for")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping(params = "age")
    public ResponseEntity<List<UserResponse>> findUsersOlderThanAge(@RequestParam @PositiveOrZero int age) {
        return ResponseEntity.ok().body(userService.findUsersOlderThan(age));
    }
}
