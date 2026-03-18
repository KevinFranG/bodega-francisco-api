package com.bodegasfrancisco.customerservice.dto;

import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UpdateCartItemDTO(
    UUID productId,

    @Positive(message = "quantity must be greater than 0")
    Integer quantity
) {
}
