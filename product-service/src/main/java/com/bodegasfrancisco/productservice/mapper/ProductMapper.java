package com.bodegasfrancisco.productservice.mapper;

import com.bodegasfrancisco.productservice.dto.CreateProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.CreateProductDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductCategoryDTO;
import com.bodegasfrancisco.productservice.model.Product;
import com.bodegasfrancisco.productservice.model.ProductCategory;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    Product toEntity(CreateProductDTO dto);

    ProductCategory toEntity(CreateProductCategoryDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductCategory merge(
        @MappingTarget ProductCategory category,
        UpdateProductCategoryDTO dto
    );
}
