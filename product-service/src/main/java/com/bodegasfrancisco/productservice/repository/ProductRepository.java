package com.bodegasfrancisco.productservice.repository;

import com.bodegasfrancisco.productservice.model.Product;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @NullMarked
    @Query("SELECT p FROM Product p WHERE p.isActive = true")
    List<Product> findAll();

    @NullMarked
    @Query("select p from Product p where p.id = :id and p.isActive = true")
    Optional<Product> findById(UUID id);
}
