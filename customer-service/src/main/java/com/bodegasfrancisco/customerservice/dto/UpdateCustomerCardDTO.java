package com.bodegasfrancisco.customerservice.dto;

import com.bodegasfrancisco.validation.constraints.HumanName;

public record UpdateCustomerCardDTO(

    @HumanName(message = "invalid card's holder name")
    String holderName,

    String cardBrand,

    String last4
) {
}
