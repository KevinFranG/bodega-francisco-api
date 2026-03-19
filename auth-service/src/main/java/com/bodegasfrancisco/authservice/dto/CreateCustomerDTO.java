package com.bodegasfrancisco.authservice.dto;

import com.bodegasfrancisco.validation.constraints.HumanName;
import com.bodegasfrancisco.validation.constraints.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public record CreateCustomerDTO(

    @NotBlank(message = "requires username")
    String username,

    @NotBlank(message = "requires password")
    String password,

    List<CreateRoleDTO> roles,

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

    public CreateCustomerDTO {
        if (roles == null)
            roles = new ArrayList<>();
    }
}
