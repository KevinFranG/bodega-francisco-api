package com.bodegasfrancisco.web;

import com.bodegasfrancisco.data.IndexService;
import com.bodegasfrancisco.web.response.ApiResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IndexEndpoint<T, ID> {
    IndexService<T, ID> getService();

    @GetMapping
    default ApiResponse<List<T>> index() {
        return ApiResponse.<List<T>>builder()
            .data(getService().index())
            .message("success")
            .build();
    }

    @GetMapping("/{id}")
    default ApiResponse<T> find(@PathVariable ID id) throws BadRequestException {
        return ApiResponse.<T>builder()
            .data(getService().find(id))
            .message("success")
            .build();
    }
}
