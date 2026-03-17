package com.bodegasfrancisco.web;

import com.bodegasfrancisco.data.CreateService;
import com.bodegasfrancisco.web.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreateEndpoint<T, CreateDTO> {
    CreateService<T, CreateDTO> getService();

    @PostMapping
    default ApiResponse<T> create(@Valid @RequestBody CreateDTO dto) {
        return ApiResponse.<T>builder()
            .message("Entity created")
            .data(getService().create(dto))
            .build();
    }
}
