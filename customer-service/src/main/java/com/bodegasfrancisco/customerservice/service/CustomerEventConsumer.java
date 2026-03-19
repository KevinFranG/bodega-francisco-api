package com.bodegasfrancisco.customerservice.service;

import com.bodegasfrancisco.kafka.Topics;
import com.bodegasfrancisco.kafka.events.CustomerCreatedEvent;
import com.bodegasfrancisco.kafka.events.CustomerCreationRequestedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor

@Component
public class CustomerEventConsumer {

    private final CustomerService service;
    private final CustomerEventProducer producer;


    @KafkaListener(
        topics = Topics.CUSTOMER_CREATION_REQUESTED,
        groupId = "customer-service"
    )
    public void handle(@NonNull CustomerCreationRequestedEvent event) {

        var customer = service.create(event);

        producer.sendEvent(
            new CustomerCreatedEvent(
                UUID.randomUUID(),
                event.correlationId(),
                customer.getId().toString(),
                customer.getEmail()
            )
        );
    }
}
