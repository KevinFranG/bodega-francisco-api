package com.bodegasfrancisco.authservice.dto;

import com.bodegasfrancisco.authservice.model.User;

public record ResponseUserDTO(
    String id,
    String username,
    String email,
    User.Roles type
) {
}
