package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.kafka.Topics;
import com.bodegasfrancisco.kafka.events.CustomerCreatedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class AuthEventConsumer {

    private final UserService service;


    @KafkaListener(
        topics = Topics.CUSTOMER_CREATED,
        groupId = "auth-service"
    )
    public void handle(@NonNull CustomerCreatedEvent event) {
        service.activeUser(event.email(), event.userId());
    }
}
