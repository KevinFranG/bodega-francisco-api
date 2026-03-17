package com.bodegasfrancisco.productservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

    @NotNull(message = "product requires name")
    private String name;

    private String description;

    @NotNull
    @Positive(message = "product requires price")
    private BigDecimal price;

    @NotNull(message = "product requires category")
    private UUID categoryId;
}
