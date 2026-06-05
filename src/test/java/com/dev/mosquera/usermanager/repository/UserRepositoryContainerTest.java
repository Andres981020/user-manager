package com.dev.mosquera.usermanager.repository;

import com.dev.mosquera.usermanager.model.User;
import com.dev.mosquera.usermanager.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
public class UserRepositoryContainerTest {

    @Autowired
    private UserRepository repository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldSaveAndFindUserById() {
        User request = User.builder()
                .name("Andres")
                .lastname("Mosquera")
                .age(27)
                .role(UserRole.ADMIN)
                .build();

        User userCreated = repository.save(request);
        Optional<User> userFound = repository.findById(userCreated.getId());

        assertTrue(userFound.isPresent());
        assertEquals("Andres", userFound.get().getName());
        assertEquals("Mosquera", userFound.get().getLastname());
        assertEquals(27, userFound.get().getAge());
        assertEquals(UserRole.ADMIN, userFound.get().getRole());
    }

    @Test
    void shouldExistsUserById() {
        User request = User.builder()
                .name("Andres")
                .lastname("Mosquera")
                .age(27)
                .role(UserRole.ADMIN)
                .build();

        User userCreated = repository.save(request);
        boolean isCreated = repository.existsById(userCreated.getId());

        assertTrue(isCreated);
    }

    @Test
    void shouldFindUserByName() {
        User request = User.builder()
                .name("Andres")
                .lastname("Mosquera")
                .age(27)
                .role(UserRole.ADMIN)
                .build();

        User userCreated = repository.save(request);
        Optional<User> userOptional = repository.findByName(userCreated.getName());

        assertTrue(userOptional.isPresent());
        assertEquals("Andres", userOptional.get().getName());
        assertEquals("Mosquera", userOptional.get().getLastname());
        assertEquals(27, userOptional.get().getAge());
        assertEquals(UserRole.ADMIN, userOptional.get().getRole());
    }

    @Test
    void shouldFindUsersByRole() {
        repository.save(createUser("Andres", "Mosquera", 27, UserRole.ADMIN));
        repository.save(createUser("Pepito", "Perez", 23, UserRole.SUPPORT));
        repository.save(createUser("John", "Doe", 35, UserRole.SUPPORT));

        List<User> userListbyRole = repository.findByRole(UserRole.SUPPORT);

        assertEquals(2, userListbyRole.size());
        assertTrue(userListbyRole.stream().allMatch(user -> user.getRole() == UserRole.SUPPORT));
    }

    @Test
    void shouldFindUsersOlderThanAge() {
        repository.save(createUser("Andres", "Mosquera", 27, UserRole.ADMIN));
        repository.save(createUser("Pepito", "Perez", 23, UserRole.SUPPORT));
        repository.save(createUser("John", "Doe", 35, UserRole.SUPPORT));

        List<User> userListbyAgeGreaterThan = repository.findByAgeGreaterThan(23);

        assertEquals(2, userListbyAgeGreaterThan.size());
        assertTrue(userListbyAgeGreaterThan.stream().allMatch(user -> user.getAge() > 23));
    }

    @Test
    void shouldReturnEmptyWhenNameDoesNotExist() {
        Optional<User> userOptional = repository.findByName("Lola");

        assertTrue(userOptional.isEmpty());
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotExist() {
        boolean exists = repository.existsById(2L);

        assertFalse(exists);
    }

    @Test
    void shouldReturnEmptyListWhenRoleDoesNotExist() {
        List<User> userListByRole = repository.findByRole(UserRole.VIP);

        assertTrue(userListByRole.isEmpty());
    }

    private User createUser(
            String name,
            String lastname,
            int age,
            UserRole role) {
        return User.builder()
                .name(name)
                .lastname(lastname)
                .age(age)
                .role(role)
                .build();
    }
}
