package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.service.notification.EmailNotificationSender;
import com.dev.mosquera.usermanager.service.notification.NotificationSender;
import com.dev.mosquera.usermanager.service.notification.NotificationService;
import com.dev.mosquera.usermanager.service.notification.SmsNotificationSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationSender emailSender;

    @Mock
    NotificationSender smsSender;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        when(emailSender.getType()).thenReturn(NotificationType.EMAIL);
        when(smsSender.getType()).thenReturn(NotificationType.SMS);

        notificationService = new NotificationService(List.of(emailSender, smsSender));
    }

    @Test
    void shouldSendEmailNotification() {
        // Act
        notificationService.send(NotificationType.EMAIL, "Sending message from emailSender");

        verify(emailSender).send("Sending message from emailSender");
        verify(smsSender, never()).send(any());

    }

    @Test
    void shouldThrowExceptionWhenUnknownType() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.send(NotificationType.TEL, "Telephone"));

        assertEquals("Notification strategy not found for type: TEL", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.send(null, "Hello"));

        assertEquals("Type cannot be null", exception.getMessage());

        verify(emailSender, never()).send(any());
        verify(smsSender, never()).send(any());
    }

}
