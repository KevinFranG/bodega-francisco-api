package com.bodegasfrancisco.productservice.service;

import com.bodegasfrancisco.data.CreateService;
import com.bodegasfrancisco.data.DeleteService;
import com.bodegasfrancisco.data.IndexService;
import com.bodegasfrancisco.data.UpdateService;
import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.productservice.dto.CreateProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductCategoryDTO;
import com.bodegasfrancisco.productservice.mapper.ProductCategoryMapper;
import com.bodegasfrancisco.productservice.model.ProductCategory;
import com.bodegasfrancisco.productservice.repository.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@Service
@RequiredArgsConstructor
public class ProductCategoryService implements
    CreateService<ProductCategory, CreateProductCategoryDTO>,
    IndexService<ProductCategory, UUID>,
    UpdateService<ProductCategory, UpdateProductCategoryDTO>,
    DeleteService<UUID> {

    private final ProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;


    @Override
    public ProductCategory create(@NonNull CreateProductCategoryDTO dto) {
        var category = mapper.toEntity(dto);

        return repository.save(category);
    }

    @Override
    public ProductCategory update(@NonNull UpdateProductCategoryDTO dto)
        throws BadRequestException {

        var category = index(dto.getId());
        category = mapper.merge(category, dto);

        return repository.save(category);
    }

    @Override
    @Transactional
    public void delete(@NonNull UUID id) throws BadRequestException {
        var category = index(id);
        category.setIsActive(false);
    }
}
