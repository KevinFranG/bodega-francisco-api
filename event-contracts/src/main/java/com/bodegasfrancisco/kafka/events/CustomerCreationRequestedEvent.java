package com.bodegasfrancisco.kafka.events;

import java.util.UUID;

public record CustomerCreationRequestedEvent(
    UUID eventId,
    UUID correlationId,
    String name,
    String lastname,
    String email,
    String phone
) {
}
