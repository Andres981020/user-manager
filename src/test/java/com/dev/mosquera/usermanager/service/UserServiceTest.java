package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.dto.UserRequest;
import com.dev.mosquera.usermanager.dto.UserResponse;
import com.dev.mosquera.usermanager.exception.UserNotFoundException;
import com.dev.mosquera.usermanager.mapper.UserMapper;
import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.model.User;
import com.dev.mosquera.usermanager.model.UserRole;
import com.dev.mosquera.usermanager.repository.UserRepository;
import com.dev.mosquera.usermanager.service.implementation.UserServiceImp;
import com.dev.mosquera.usermanager.service.notification.NotificationService;
import com.dev.mosquera.usermanager.service.notification.RoleNotificationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @Mock
    NotificationService notificationService;

    @Mock
    RoleNotificationManager manager;

    @InjectMocks
    UserServiceImp service;

    @Test
    void shouldFindUserById() {
        // Initial conditions
        User userTest = User.builder()
                .id(1L)
                .name("Andres")
                .lastname("Mosquera")
                .username("andresMA")
                .password("admin123")
                .age(27)
                .build();

        UserResponse responseTest = UserResponse.builder()
                .id(1L)
                .name("Andres")
                .lastname("Mosquera")
                .username("andresMA")
                .age(27)
                .build();

        // Act
        when(repository.findById(1L)).thenReturn(Optional.of(userTest));
        when(mapper.mapToResponse(userTest)).thenReturn(responseTest);

        UserResponse result = service.findById(1L);

        // Assertions
        assertEquals(1L, result.getId());
        assertEquals("Andres", result.getName());
        assertEquals("Mosquera", result.getLastname());
        assertEquals(27, result.getAge());

        verify(repository).findById(1L);
        verify(mapper).mapToResponse(userTest);
    }

    @Test
    void shouldCreateUser() {
        UserRequest userRequest = UserRequest.builder().name("Andres").lastname("Mosquera").age(27).build();
        User userMapped = User.builder().id(1L).name("Andres").lastname("Mosquera").age(27).build();
        UserResponse response = UserResponse.builder().id(1L).name("Andres").lastname("Mosquera").age(27).build();

        when(repository.existsById(1L)).thenReturn(false);
        when(mapper.mapToUser(userRequest)).thenReturn(userMapped);
        when(repository.save(userMapped)).thenReturn(userMapped);
        when(mapper.mapToResponse(userMapped)).thenReturn(response);

        UserResponse userCreated = service.createUser(userRequest);

        assertEquals(1L, userCreated.getId());
        assertEquals("Andres", userCreated.getName());
        assertEquals("Mosquera", userCreated.getLastname());
        assertEquals(27, userCreated.getAge());

        verify(repository).existsById(1L);
        verify(repository).save(userMapped);
        verify(mapper).mapToUser(userRequest);
        verify(mapper).mapToResponse(userMapped);
    }


    @Test
    void shouldThrowExceptionUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.findById(1L));
        verify(repository).findById(1L);
        verify(mapper, never()).mapToResponse(any());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        UserRequest userRequest = UserRequest.builder()
                .name("Andres")
                .lastname("Mosquera")
                .age(27)
                .build();

        when(repository.existsById(1L)).thenReturn(true);

         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createUser(userRequest));

        assertEquals("The user with id 1 is already created", exception.getMessage());

        verify(repository).existsById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionByAge() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.findUsersOlderThan(-1));

        assertEquals("The age should be 0 or greater", exception.getMessage());

        verify(repository, never()).findByAgeGreaterThan(-1);
    }

    @Test
    void shouldCreateUserWithOrchestration() {
        UserRequest userRequest = UserRequest.builder().name("Peter").lastname("Parker").age(16).role(UserRole.CUSTOMER).build();
        User userMapped = User.builder().id(2L).name("Peter").lastname("Parker").age(16).role(UserRole.CUSTOMER).build();
        UserResponse response = UserResponse.builder().id(2L).name("Peter").lastname("Parker").age(16).role(UserRole.CUSTOMER).build();

        when(repository.existsById(2L)).thenReturn(false);
        when(mapper.mapToUser(userRequest)).thenReturn(userMapped);
        when(repository.save(userMapped)).thenReturn(userMapped);
        when(manager.resolve(userMapped.getRole())).thenReturn(NotificationType.SMS);
        when(mapper.mapToResponse(userMapped)).thenReturn(response);

        UserResponse userCreated = service.createUser(userRequest);

        assertEquals(2L, userCreated.getId());
        assertEquals("Peter", userCreated.getName());
        assertEquals("Parker", userCreated.getLastname());
        assertEquals(16, userCreated.getAge());
        assertEquals(UserRole.CUSTOMER, userCreated.getRole());

        verify(repository).existsById(2L);
        verify(repository).save(userMapped);
        verify(mapper).mapToUser(userRequest);
        verify(manager).resolve(UserRole.CUSTOMER);
        verify(notificationService).send(NotificationType.SMS, "Welcome Peter Parker");
        verify(mapper).mapToResponse(userMapped);
    }
}
