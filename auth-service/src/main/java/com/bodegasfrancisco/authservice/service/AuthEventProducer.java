package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.kafka.Topics;
import com.bodegasfrancisco.kafka.events.CustomerCreationRequestedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class AuthEventProducer {

    private final KafkaTemplate<String, Object> template;


    public void sendEvent(@NonNull CustomerCreationRequestedEvent event) {
        template.send(
            Topics.CUSTOMER_CREATION_REQUESTED,
            event.correlationId().toString(),
            event
        );
    }
}
