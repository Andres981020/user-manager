package com.dev.mosquera.usermanager.service.notification;

import com.dev.mosquera.usermanager.model.NotificationType;
import com.dev.mosquera.usermanager.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultRoleNotificationManager implements RoleNotificationManager {

    private final Map<UserRole, NotificationType> roleNotificationMapping;

    public DefaultRoleNotificationManager() {
        this.roleNotificationMapping = Map.of(
                UserRole.ADMIN, NotificationType.EMAIL,
                UserRole.CUSTOMER, NotificationType.SMS,
                UserRole.SUPPORT, NotificationType.SMS
        );
    }

    @Override
    public NotificationType resolve(UserRole role) {
        if (role == null) throw new IllegalArgumentException("User role shouldn't be null");

        NotificationType type = roleNotificationMapping.get(role);

        if(type == null) throw new IllegalArgumentException("Notification type not found for role: " + role);
        return type;
    }
}
