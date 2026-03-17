package com.bodegasfrancisco.productservice.controller;

import com.bodegasfrancisco.productservice.dto.CreateProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductCategoryDTO;
import com.bodegasfrancisco.productservice.model.ProductCategory;
import com.bodegasfrancisco.productservice.service.ProductCategoryService;
import com.bodegasfrancisco.web.CreateEndpoint;
import com.bodegasfrancisco.web.DeleteEndpoint;
import com.bodegasfrancisco.web.IndexEndpoint;
import com.bodegasfrancisco.web.UpdateEndpoint;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Data
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ProductCategoryController implements
    CreateEndpoint<ProductCategory, CreateProductCategoryDTO>,
    UpdateEndpoint<ProductCategory, UpdateProductCategoryDTO>,
    IndexEndpoint<ProductCategory, UUID>,
    DeleteEndpoint<UUID> {

    private final ProductCategoryService service;
}
