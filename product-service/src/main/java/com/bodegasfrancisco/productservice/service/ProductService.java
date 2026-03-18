package com.bodegasfrancisco.productservice.service;

import com.bodegasfrancisco.data.CreateService;
import com.bodegasfrancisco.data.DeleteService;
import com.bodegasfrancisco.data.IndexService;
import com.bodegasfrancisco.data.UpdateService;
import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.productservice.dto.CreateProductDTO;
import com.bodegasfrancisco.productservice.dto.UpdateProductDTO;
import com.bodegasfrancisco.productservice.mapper.ProductMapper;
import com.bodegasfrancisco.productservice.model.Product;
import com.bodegasfrancisco.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class ProductService implements
    CreateService<Product, CreateProductDTO>,
    IndexService<Product, UUID>,
    UpdateService<Product, UpdateProductDTO>,
    DeleteService<UUID> {

    private final ProductRepository repository;
    private final ProductMapper mapper;


    @Override
    public Product create(@NonNull CreateProductDTO createProductDTO) {
        var product = mapper.toEntity(createProductDTO);
        product.setSku(generateSku(product));

        return repository.save(product);
    }

    @Override
    @Transactional
    public void delete(@NonNull UUID uuid) throws BadRequestException {
        var product = index(uuid);
        product.setIsActive(false);
    }

    @Override
    public Product update(@NonNull UpdateProductDTO updateProductDTO)
        throws BadRequestException {
        var product = index(updateProductDTO.getId());
        product = mapper.merge(product, updateProductDTO);

        if (updateProductDTO.getName() != null)
            product.setSku(generateSku(product));

        return repository.save(product);
    }

    private @NonNull String generateSku(@NonNull Product product) {
        var normalized = Normalizer.normalize(product.getName(), Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")
            .toUpperCase()
            .replaceAll("[^A-Z0-9\\s]", "")
            .replaceAll("\\s+", " ")
            .trim();
        var name = Arrays.stream(normalized.split(" "))
            .filter(word -> !word.isBlank())
            .limit(3)
            .map(word -> word.length() <= 3 ? word : word.substring(0, 3))
            .collect(Collectors.joining("-"));

        return name + "-" + repository.getNextSkuId();
    }
}
