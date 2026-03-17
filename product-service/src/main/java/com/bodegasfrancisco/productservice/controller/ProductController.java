package com.bodegasfrancisco.productservice.controller;

import com.bodegasfrancisco.productservice.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class ProductController {
    static final private List<Product> products = List.of();

    @GetMapping
    public List<Product> getAll() {
        return products;
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return products.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}
