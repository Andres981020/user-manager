package com.dev.mosquera.usermanager.service.notification;

import com.dev.mosquera.usermanager.model.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender {
    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public short send(String message) {
        System.out.println("Sending message from Sms");
        return 0;
    }
}
