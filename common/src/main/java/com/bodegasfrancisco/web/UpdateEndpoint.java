package com.bodegasfrancisco.web;

import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.web.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateEndpoint<UpdateResonse, UpdateDTO> {

    @PutMapping
    ApiResponse<UpdateResonse> update(@Valid @RequestBody UpdateDTO dto)
        throws BadRequestException;
}
