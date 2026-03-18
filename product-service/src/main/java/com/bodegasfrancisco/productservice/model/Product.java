package com.bodegasfrancisco.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name"),
        @UniqueConstraint(columnNames = "sku"),
    }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sku;

    @Column(length = 100)
    private String description;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @Column(nullable = false)
    @DecimalMin("0.01")
    private BigDecimal price;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "category_id",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private ProductCategory category;
}
