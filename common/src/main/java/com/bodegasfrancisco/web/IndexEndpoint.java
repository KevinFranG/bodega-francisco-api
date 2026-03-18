package com.bodegasfrancisco.web;

import com.bodegasfrancisco.web.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IndexEndpoint<IndexResponse, ID> {

    @GetMapping
    ApiResponse<List<IndexResponse>> index();

    @GetMapping("/{id}")
    ApiResponse<IndexResponse> index(@PathVariable ID id);
}
