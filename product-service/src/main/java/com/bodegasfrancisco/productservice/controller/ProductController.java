package com.bodegasfrancisco.productservice.controller;

import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.productservice.dto.CreateProductDTO;
import com.bodegasfrancisco.productservice.dto.ResponseProductDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductDTO;
import com.bodegasfrancisco.productservice.mapper.ProductMapper;
import com.bodegasfrancisco.productservice.service.ProductService;
import com.bodegasfrancisco.web.CreateEndpoint;
import com.bodegasfrancisco.web.DeleteEndpoint;
import com.bodegasfrancisco.web.IndexEndpoint;
import com.bodegasfrancisco.web.UpdateEndpoint;
import com.bodegasfrancisco.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController implements
    CreateEndpoint<ResponseProductDTO, CreateProductDTO>,
    UpdateEndpoint<ResponseProductDTO, UpdateProductDTO>,
    IndexEndpoint<ResponseProductDTO, UUID>,
    DeleteEndpoint<UUID> {

    private final ProductService service;
    private final ProductMapper mapper;


    @Override
    public ApiResponse<ResponseProductDTO> create(CreateProductDTO dto) {
        var product = service.create(dto);

        return ApiResponse.<ResponseProductDTO>builder()
            .data(mapper.toResponse(product))
            .message("product created successfully")
            .build();
    }

    @Override
    public void delete(UUID id) throws BadRequestException {
        service.delete(id);
    }

    @Override
    public ApiResponse<List<ResponseProductDTO>> index() {
        return ApiResponse.<List<ResponseProductDTO>>builder()
            .data(service.index().stream()
                      .map(mapper::toResponse)
                      .toList())
            .build();
    }

    @Override
    public ApiResponse<ResponseProductDTO> index(UUID id) {
        return ApiResponse.<ResponseProductDTO>builder()
            .data(mapper.toResponse(service.index(id)))
            .build();
    }

    @Override
    public ApiResponse<ResponseProductDTO> update(UpdateProductDTO dto)
        throws BadRequestException {
        var product = service.update(dto);

        return ApiResponse.<ResponseProductDTO>builder()
            .data(mapper.toResponse(product))
            .message("product updated successfully")
            .build();
    }
}
