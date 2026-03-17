package com.bodegasfrancisco.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private UUID productId; // from Products

    @Column(nullable = false)
    @Min(0)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    @DecimalMin("0.00")
    private BigDecimal unitPrice;

    @Column(nullable = false)
    @DecimalMin("0.00")
    private BigDecimal discount;

    @Transient
    private @NonNull BigDecimal getSubtotal() {
        return unitPrice.multiply(discount).subtract(discount);
    }
}