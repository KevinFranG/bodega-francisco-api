package com.bodegasfrancisco.customerservice.dto;

public record ResponseCustomerCardDTO(
    String holderName,
    String cardBrand,
    String last4,
    Boolean isDefault
) {
}
