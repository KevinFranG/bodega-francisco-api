package com.bodegasfrancisco.productservice.repository;

import com.bodegasfrancisco.productservice.model.ProductCategory;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
    @NullMarked
    @Query("SELECT c FROM ProductCategory c WHERE c.isActive = true")
    @EntityGraph(attributePaths = { "children" })
    List<ProductCategory> findAll();

    @NullMarked
    @Query("select c from ProductCategory c where c.id = :id and c.isActive = true")
    @EntityGraph(attributePaths = { "children" })
    Optional<ProductCategory> findById(UUID id);
}
