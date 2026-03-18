package com.bodegasfrancisco.customerservice.dto;

public record ResponseCustomerAddressDTO(
    String label,
    String city,
    String state,
    String postalCode,
    String line1,
    Boolean isDefault
) {
}
