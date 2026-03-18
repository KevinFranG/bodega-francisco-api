package com.bodegasfrancisco.productservice.dto;

import com.bodegasfrancisco.validation.constraints.NotBlankIfPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {

    @NotNull(message = "product requires id when updating")
    private UUID id;

    @NotBlankIfPresent(message = "product requires a not blank name")
    private String name;

    private String description;

    @Positive(message = "product requires price")
    private BigDecimal price;

    private UUID categoryId;

    @URL(protocol = "https", message = "product's image may be a valid URL")
    private String imageUrl;
}
