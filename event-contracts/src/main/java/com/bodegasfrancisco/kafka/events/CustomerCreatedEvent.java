package com.bodegasfrancisco.kafka.events;

import java.util.UUID;

public record CustomerCreatedEvent(
    UUID eventId,
    UUID correlationId,
    String userId,
    String email
) {
}
