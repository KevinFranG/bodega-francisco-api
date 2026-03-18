package com.bodegasfrancisco.customerservice.dto;

import com.bodegasfrancisco.validation.constraints.HumanName;
import com.bodegasfrancisco.validation.constraints.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateCustomerDTO(

    @NotNull(message = "requires id")
    String id,

    @HumanName(message = "invalid customer's name")
    String name,

    @HumanName(message = "invalid customer's lastname")
    String lastname,

    @Email(message = "invalid customer's email")
    String email,

    @PhoneNumber(message = "invalid customer's phone number")
    String phone
) {
}
