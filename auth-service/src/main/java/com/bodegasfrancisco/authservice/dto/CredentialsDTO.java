package com.bodegasfrancisco.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CredentialsDTO(

    @NotBlank(message = "required email")
    @Email(message = "invalid email")
    String email,

    @NotBlank(message = "required password")
    String password
) {
}
