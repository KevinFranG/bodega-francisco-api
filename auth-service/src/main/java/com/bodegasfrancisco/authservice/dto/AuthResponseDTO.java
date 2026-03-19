package com.bodegasfrancisco.authservice.dto;

public record AuthResponseDTO(
    String accessToken,
    String refreshToken,
    String type,
    long expiresIn
) {
}
