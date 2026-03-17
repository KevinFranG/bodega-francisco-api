package com.bodegasfrancisco.productservice.service;

import com.bodegasfrancisco.data.CreateService;
import com.bodegasfrancisco.data.DeleteService;
import com.bodegasfrancisco.data.IndexService;
import com.bodegasfrancisco.data.UpdateService;
import com.bodegasfrancisco.productservice.dto.CreateProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductCategoryDTO;
import com.bodegasfrancisco.productservice.mapper.ProductMapper;
import com.bodegasfrancisco.productservice.model.ProductCategory;
import com.bodegasfrancisco.productservice.repository.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
    private final ProductMapper mapper;


    public ProductCategory create(@NonNull CreateProductCategoryDTO dto) {
        var category = mapper.toEntity(dto);

        return repository.save(category);
    }

    public ProductCategory update(@NonNull UpdateProductCategoryDTO dto)
        throws BadRequestException {

        var category = find(dto.getId());
        category = mapper.merge(category, dto);

        return repository.save(category);
    }

    @Transactional
    public void delete(@NonNull UUID id) throws BadRequestException {
        var category = find(id);
        category.setIsActive(false);
    }
}
