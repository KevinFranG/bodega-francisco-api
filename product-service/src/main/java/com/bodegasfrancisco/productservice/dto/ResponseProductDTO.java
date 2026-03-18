package com.bodegasfrancisco.productservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ResponseProductDTO(
    UUID id,
    String name,
    String sku,
    String description,
    String imageUrl,
    BigDecimal price,
    ResponseProductCategoryDTO category
) {
}
