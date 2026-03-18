package com.bodegasfrancisco.productservice.mapper;

import com.bodegasfrancisco.productservice.dto.*;
import com.bodegasfrancisco.productservice.model.Product;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = ProductCategoryMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toEntity(CreateProductDTO dto);

    @Mapping(
        target = "category",
        source = "category",
        qualifiedByName = "categoryBasic")
    ResponseProductDTO toResponse(Product product);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product merge(
        @MappingTarget Product category,
        UpdateProductDTO dto
    );


}
