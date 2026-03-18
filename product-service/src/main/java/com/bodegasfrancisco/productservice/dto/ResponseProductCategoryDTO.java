package com.bodegasfrancisco.productservice.dto;

import java.util.UUID;

public record ResponseProductCategoryDTO(
    UUID id,
    String code,
    String name,
    String description,
    UUID parentId
) {
}
