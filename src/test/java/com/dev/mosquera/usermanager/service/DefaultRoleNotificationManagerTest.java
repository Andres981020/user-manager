package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.model.UserRole;
import com.dev.mosquera.usermanager.service.notification.DefaultRoleNotificationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class DefaultRoleNotificationManagerTest {
    @Test
    void shouldNotificationTypeForAdmin() {
        // Initial Conditions
        DefaultRoleNotificationManager manager = new DefaultRoleNotificationManager();
        // Act
        NotificationType resultAdmin = manager.resolve(UserRole.ADMIN);
        // Assertions
        assertEquals(NotificationType.EMAIL, resultAdmin);
    }
    @Test
    void shouldNotificationTypeForCustomer() {
        // Initial Conditions
        DefaultRoleNotificationManager manager = new DefaultRoleNotificationManager();
        // Act
        NotificationType resultCustomer = manager.resolve(UserRole.CUSTOMER);
        // Assertions
        assertEquals(NotificationType.SMS, resultCustomer);

    }
    @Test
    void shouldNotificationTypeforSupport() {
        // Initial Conditions
        DefaultRoleNotificationManager manager = new DefaultRoleNotificationManager();
        // Act
        NotificationType resultSupport = manager.resolve(UserRole.SUPPORT);
        // Assertions
        assertEquals(NotificationType.SMS, resultSupport);
    }

    @Test
    void shouldThrowExceptionWhenRoleIsNull() {
        // Initial Conditions
        DefaultRoleNotificationManager manager = new DefaultRoleNotificationManager();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> manager.resolve(null));

        assertEquals("User role shouldn't be null", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenRoleIsNotMapped() {
        // Initial Conditions
        DefaultRoleNotificationManager manager = new DefaultRoleNotificationManager();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> manager.resolve(UserRole.VIP));

        assertEquals("Notification type not found for role: VIP", exception.getMessage());
    }
}
