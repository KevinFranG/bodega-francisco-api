package com.bodegasfrancisco.web;

import com.bodegasfrancisco.data.UpdateService;
import com.bodegasfrancisco.web.response.ApiResponse;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateEndpoint<T, UpdateDTO> {
    UpdateService<T, UpdateDTO> getService();

    @PutMapping
    default ApiResponse<T> update(@Valid @RequestBody UpdateDTO dto) throws BadRequestException {
        return ApiResponse.<T>builder()
            .data(getService().update(dto))
            .message("entity updated")
            .build();
    }
}
