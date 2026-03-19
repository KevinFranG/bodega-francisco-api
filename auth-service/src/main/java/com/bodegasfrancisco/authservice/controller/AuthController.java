package com.bodegasfrancisco.authservice.controller;

import com.bodegasfrancisco.authservice.dto.AuthResponseDTO;
import com.bodegasfrancisco.authservice.dto.CreateAdminDTO;
import com.bodegasfrancisco.authservice.dto.CredentialsDTO;
import com.bodegasfrancisco.authservice.dto.CreateCustomerDTO;
import com.bodegasfrancisco.authservice.service.AuthService;
import com.bodegasfrancisco.web.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor

@RestController
@RequestMapping
public class AuthController {

    private final AuthService service;


    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(@Valid @RequestBody CredentialsDTO credential) {
        return ApiResponse.<AuthResponseDTO>builder()
            .data(service.login(credential))
            .message("login successful")
            .build();
    }

    @PostMapping("/customer/register")
    public ApiResponse<AuthResponseDTO> register(@Valid @RequestBody CreateCustomerDTO dto) {
        return ApiResponse.<AuthResponseDTO>builder()
            .data(service.register(dto))
            .message("register successful")
            .build();
    }

    @PostMapping("/admin/register")
    public ApiResponse<AuthResponseDTO> register(@Valid @RequestBody CreateAdminDTO dto) {
        return ApiResponse.<AuthResponseDTO>builder()
            .data(service.register(dto))
            .message("register successful")
            .build();
    }
}
