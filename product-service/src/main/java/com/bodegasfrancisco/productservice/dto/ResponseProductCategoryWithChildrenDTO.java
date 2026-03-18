package com.bodegasfrancisco.productservice.dto;

import java.util.List;
import java.util.UUID;

public record ResponseProductCategoryWithChildrenDTO(
    UUID id,
    String code,
    String name,
    String description,
    UUID parentId,
    List<ResponseProductCategoryDTO> children
) {
}
