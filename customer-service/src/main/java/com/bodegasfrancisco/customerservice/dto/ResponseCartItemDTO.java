package com.bodegasfrancisco.customerservice.dto;

import java.time.Instant;
import java.util.UUID;

public record ResponseCartItemDTO(
    UUID productId,
    Integer quantity,
    Instant expiresAt
) {
}
