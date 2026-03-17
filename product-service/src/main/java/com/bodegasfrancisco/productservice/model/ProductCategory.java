package com.bodegasfrancisco.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
    name = "product_categories",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "name")
    })
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "parent_id")
    private UUID parentId = null;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "parent_id",
        insertable = false,
        updatable = false
    )
    private ProductCategory parent = null;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ProductCategory> children = new ArrayList<>();
}
