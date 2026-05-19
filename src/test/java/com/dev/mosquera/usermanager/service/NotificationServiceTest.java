package com.dev.mosquera.usermanager.service;

import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.service.notification.EmailNotificationSender;
import com.dev.mosquera.usermanager.service.notification.NotificationSender;
import com.dev.mosquera.usermanager.service.notification.NotificationService;
import com.dev.mosquera.usermanager.service.notification.SmsNotificationSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    EmailNotificationSender emailSender;

    @Mock
    SmsNotificationSender smsSender;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void shouldSendEmailNotification() {
        // Initial conditions
        List<NotificationSender> senders = List.of(emailSender, smsSender);
        NotificationType email = NotificationType.EMAIL;
        String message = "Email notification";
        Map<NotificationType, NotificationSender> strategies = senders.stream().collect(Collectors.toMap(NotificationSender::getType, Function.identity()));

        // Act
        when(strategies.get(NotificationType.EMAIL)).thenReturn(emailSender);

        // Assertions
        assertEquals("Email notification", strategies.get(email).send(message));


    }
}
