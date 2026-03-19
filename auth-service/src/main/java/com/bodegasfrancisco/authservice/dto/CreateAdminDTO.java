package com.bodegasfrancisco.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public record CreateAdminDTO(

    @NotBlank(message = "requires username")
    String username,

    @NotBlank(message = "requires customer's email")
    @Email(message = "invalid customer's email")
    String email,

    @NotBlank(message = "requires password")
    String password,

    List<CreateRoleDTO> roles
) {

    public CreateAdminDTO {
        if (roles == null)
            roles = new ArrayList<>();
    }
}
