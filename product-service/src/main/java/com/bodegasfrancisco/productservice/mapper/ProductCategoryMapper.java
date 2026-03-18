package com.bodegasfrancisco.productservice.mapper;

import com.bodegasfrancisco.productservice.dto.CreateProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.ResponseProductCategoryDTO;
import com.bodegasfrancisco.productservice.dto.ResponseProductCategoryWithChildrenDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductCategoryDTO;
import com.bodegasfrancisco.productservice.model.ProductCategory;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCategoryMapper {

    ProductCategory toEntity(CreateProductCategoryDTO dto);

    @Named("categoryBasic")
    ResponseProductCategoryDTO toResponse(ProductCategory category);

    @Mapping(
        target = "children",
        source = "children",
        qualifiedByName = "categoryBasic")
    @Named("categoryWithChildren")
    ResponseProductCategoryWithChildrenDTO toResponseWithChildren(ProductCategory category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductCategory merge(
        @MappingTarget ProductCategory category,
        UpdateProductCategoryDTO dto
    );
}
