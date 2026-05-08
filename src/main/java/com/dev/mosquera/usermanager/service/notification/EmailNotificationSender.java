package com.dev.mosquera.usermanager.service.notification;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending message from emailSender");
    }
}
