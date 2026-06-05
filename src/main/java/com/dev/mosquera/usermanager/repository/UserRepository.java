package com.dev.mosquera.usermanager.repository;

import com.dev.mosquera.usermanager.model.User;
import com.dev.mosquera.usermanager.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAgeGreaterThan(int age);
    List<User> findByRole(UserRole role);
    Optional<User> findByName(String name);
}
