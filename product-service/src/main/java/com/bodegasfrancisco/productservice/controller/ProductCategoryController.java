package com.bodegasfrancisco.productservice.controller;

import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.productservice.dto.CreateProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.ResponseProductCategoryWithChildrenDTO;
import com.bodegasfrancisco.productservice.dto.ResponseProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductCategoryDTO;
import com.bodegasfrancisco.productservice.mapper.ProductCategoryMapper;
import com.bodegasfrancisco.productservice.service.ProductCategoryService;
import com.bodegasfrancisco.web.CreateEndpoint;
import com.bodegasfrancisco.web.DeleteEndpoint;
import com.bodegasfrancisco.web.IndexEndpoint;
import com.bodegasfrancisco.web.UpdateEndpoint;
import com.bodegasfrancisco.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ProductCategoryController implements
    CreateEndpoint<ResponseProductCategoryDTO, CreateProductCategoryDTO>,
    UpdateEndpoint<ResponseProductCategoryDTO, UpdateProductCategoryDTO>,
    IndexEndpoint<ResponseProductCategoryWithChildrenDTO, UUID>,
    DeleteEndpoint<UUID> {

    private final ProductCategoryService service;
    private final ProductCategoryMapper mapper;


    @Override
    public ApiResponse<ResponseProductCategoryDTO> create(CreateProductCategoryDTO dto) {
        var category = service.create(dto);

        return ApiResponse.<ResponseProductCategoryDTO>builder()
            .data(mapper.toResponse(category))
            .message("product category created")
            .build();
    }

    @Override
    public ApiResponse<ResponseProductCategoryDTO> update(UpdateProductCategoryDTO dto)
        throws BadRequestException {

        var category = service.update(dto);

        return ApiResponse.<ResponseProductCategoryDTO>builder()
            .data(mapper.toResponse(category))
            .message("product category updated")
            .build();
    }

    @Override
    public ApiResponse<List<ResponseProductCategoryWithChildrenDTO>> index() {
        return ApiResponse.<List<ResponseProductCategoryWithChildrenDTO>>builder()
            .data(
                service.index()
                    .stream()
                    .map(mapper::toResponseWithChildren)
                    .toList()
            )
            .message("success")
            .build();
    }

    @Override
    public ApiResponse<ResponseProductCategoryWithChildrenDTO> index(UUID id) {
        return ApiResponse.<ResponseProductCategoryWithChildrenDTO>builder()
            .data(mapper.toResponseWithChildren(service.index(id)))
            .message("success")
            .build();
    }

    @Override
    public void delete(UUID id) throws BadRequestException {
        service.delete(id);
    }
}
