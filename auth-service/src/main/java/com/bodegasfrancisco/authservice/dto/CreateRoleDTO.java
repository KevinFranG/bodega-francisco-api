package com.bodegasfrancisco.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleDTO(

    @NotBlank(message = "requires name")
    String name,

    String description
) {
}
