package com.bodegasfrancisco.customerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerAddressDTO(
    
    @NotBlank(message = "requires address's label")
    String label,

    @NotBlank(message = "requires address's city")
    String city,

    @NotBlank(message = "requires address's state")
    String state,

    @NotBlank(message = "requires address's postal code")
    String postalCode,

    @NotBlank(message = "requires address's line 1")
    String line1
) {
}
