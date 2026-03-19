package com.bodegasfrancisco.authservice.controller;

import com.bodegasfrancisco.authservice.dto.ResponseUserDTO;
import com.bodegasfrancisco.authservice.mapper.UserMapper;
import com.bodegasfrancisco.authservice.service.UserService;
import com.bodegasfrancisco.web.IndexEndpoint;
import com.bodegasfrancisco.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/users")
public class UserController implements
    IndexEndpoint<ResponseUserDTO, String> {

    private final UserService service;
    private final UserMapper mapper;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ResponseUserDTO>> index() {
        return ApiResponse.<List<ResponseUserDTO>>builder()
            .data(service.index()
                      .stream()
                      .map(mapper::toResponse)
                      .toList())
            .message("success")
            .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ResponseUserDTO> index(String id) {
        return ApiResponse.<ResponseUserDTO>builder()
            .data(mapper.toResponse(service.index(id)))
            .message("user found")
            .build();
    }
}
