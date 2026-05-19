package com.dev.mosquera.usermanager.service.notification;

import com.dev.mosquera.usermanager.model.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationSender {
    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public short send(String message) {
        System.out.println("Sending message from emailSender");
        return 0;
    }
}
