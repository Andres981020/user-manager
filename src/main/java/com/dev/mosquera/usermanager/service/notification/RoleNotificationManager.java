package com.dev.mosquera.usermanager.service.notification;

import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.model.UserRole;

public interface RoleNotificationManager {
    NotificationType resolve(UserRole role);
}
