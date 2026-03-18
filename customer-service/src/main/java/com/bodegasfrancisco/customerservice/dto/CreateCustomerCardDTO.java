package com.bodegasfrancisco.customerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerCardDTO(

    @NotBlank(message = "requires card's holder name")
    String holderName,

    @NotBlank(message = "requires card's brand")
    String cardBrand,

    @NotBlank(message = "requires card's number")
    String last4
) {
}
