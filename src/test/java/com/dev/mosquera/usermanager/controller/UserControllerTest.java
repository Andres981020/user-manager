package com.dev.mosquera.usermanager.controller;

import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void shouldReturnUserById() {

        // Initial Conditions
        UserResponse response = UserResponse.builder().id(1L).name("Andres").lastname("Mosquera").age(27).build();

    }
}
