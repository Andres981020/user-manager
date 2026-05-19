package com.dev.mosquera.usermanager.service.notification;

import com.dev.mosquera.usermanager.model.NotificationType;

public interface NotificationSender {
    NotificationType getType();
    short send(String message);
}
