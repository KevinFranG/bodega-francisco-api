package com.bodegasfrancisco.productservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCategoryDTO {

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    private UUID parentId;
}
