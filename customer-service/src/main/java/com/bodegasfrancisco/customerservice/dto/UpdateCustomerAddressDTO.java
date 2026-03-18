package com.bodegasfrancisco.customerservice.dto;

public record UpdateCustomerAddressDTO(

    String label,

    String city,

    String state,

    String postalCode,

    String line1
) {
}
