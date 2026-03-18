package com.bodegasfrancisco.customerservice.dto;

import com.bodegasfrancisco.validation.constraints.HumanName;
import com.bodegasfrancisco.validation.constraints.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDTO(

    @NotBlank(message = "requires customer's name")
    @HumanName(message = "invalid customer's name")
    String name,

    @NotBlank(message = "requires customer's lastname")
    @HumanName(message = "invalid customer's lastname")
    String lastname,

    @NotBlank(message = "requires customer's email")
    @Email(message = "invalid customer's email")
    String email,

    @NotBlank(message = "requires customer's phone number")
    @PhoneNumber(message = "invalid customer's phone number")
    String phone
) {
}
