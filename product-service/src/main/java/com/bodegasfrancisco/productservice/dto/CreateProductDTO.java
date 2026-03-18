package com.bodegasfrancisco.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

    @NotBlank(message = "product requires a not blank name")
    private String name;

    private String description;

    @NotNull
    @Positive(message = "product requires price")
    private BigDecimal price;

    @NotNull(message = "product requires category")
    private UUID categoryId;

    @URL(protocol = "https", message = "product's image may be a valid URL")
    private String imageUrl;
}
