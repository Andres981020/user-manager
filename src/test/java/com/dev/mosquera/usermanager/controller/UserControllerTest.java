package com.dev.mosquera.usermanager.controller;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.exception.UserNotFoundException;
import com.dev.mosquera.usermanager.model.UserRole;
import com.dev.mosquera.usermanager.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnUserById() throws Exception {

        // Initial Conditions
        UserResponse response = UserResponse.builder().id(1L).name("Andres").lastname("Mosquera").age(27).role(UserRole.VIP).build();

        // Act
        when(userService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Andres"))
                .andExpect(jsonPath("$.lastname").value("Mosquera"))
                .andExpect(jsonPath("$.age").value(27))
                .andExpect(jsonPath("$.role").value("VIP"));

        verify(userService).findById(1L);
    }

    @Test
    void shouldReturn404WhenUserDoesNotExist() throws Exception {

        when(userService.findById(2L)).thenThrow(new UserNotFoundException("The user with id 2 was not found!"));

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The user with id 2 was not found!"))
                .andExpect(jsonPath("$.status").value(404));

        verify(userService).findById(2L);
    }

    @Test
    void shouldReturn400WhenRequestBodyIsInvalid() throws Exception {

        UserRequest request = UserRequest.builder().id(1L).name("Andres").lastname("Mosquera").age(-27).role(UserRole.VIP).build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.age").exists());


        verifyNoInteractions(userService);
    }

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        UserRequest request = UserRequest.builder().id(1L).name("Andres").lastname("Mosquera").age(27).role(UserRole.VIP).build();
        UserResponse response = UserResponse.builder().id(1L).name("Andres").lastname("Mosquera").age(27).role(UserRole.VIP).build();

        when(userService.createUser(request)).thenReturn(response);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Andres"))
                .andExpect(jsonPath("$.lastname").value("Mosquera"))
                .andExpect(jsonPath("$.age").value(27))
                .andExpect(jsonPath("$.role").value("VIP"));

        verify(userService).createUser(request);
    }
}
