package com.bodegasfrancisco.customerservice.service;

import com.bodegasfrancisco.kafka.Topics;
import com.bodegasfrancisco.kafka.events.CustomerCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class CustomerEventProducer {

    private final KafkaTemplate<String, Object> template;


    public void sendEvent(CustomerCreatedEvent event) {
        template.send(
            Topics.CUSTOMER_CREATED,
            event.correlationId().toString(),
            event
        );
    }
}
