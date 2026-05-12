package com.dev.mosquera.usermanager.service.notification;

import com.dev.mosquera.usermanager.model.NotificationType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final Map<NotificationType, NotificationSender> strategies;

    public NotificationService(List<NotificationSender> senders) {
        strategies = senders.stream().collect(Collectors.toMap(NotificationSender::getType, Function.identity()));
    }

}
