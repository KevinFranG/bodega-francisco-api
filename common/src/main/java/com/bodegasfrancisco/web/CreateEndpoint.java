package com.bodegasfrancisco.web;

import com.bodegasfrancisco.web.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CreateEndpoint<CreateResponse, CreateDTO> {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<CreateResponse> create(@Valid @RequestBody CreateDTO dto);
}
