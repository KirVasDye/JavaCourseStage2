package com.exemple.app.kafka;

import dto.Operation;
import dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.exemple.app.service.EmailService;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    @KafkaListener(
            topics = "user-events",
            groupId = "notification-group"
    )
    public void listen(UserEvent event) {

        if (event.operation() == Operation.CREATED) {
            emailService.sendCreated(event.email());
        }

        if (event.operation() == Operation.DELETED) {
            emailService.sendDeleted(event.email());
        }
    }
}
