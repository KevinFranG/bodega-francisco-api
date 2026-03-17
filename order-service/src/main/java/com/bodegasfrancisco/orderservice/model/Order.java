package com.bodegasfrancisco.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = "orderNumber")})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "attended_employee_id")
    private UUID attendedEmployeeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Types type = Types.DELIVER;

    @Column(nullable = false, precision = 12, scale = 2)
    @DecimalMin("0.01")
    private BigDecimal subtotal;

    @Column(name = "discount_total", nullable = false, precision = 12, scale = 2)
    @DecimalMin("0.00")
    private BigDecimal discountTotal;

    @Column(name = "shipping_cost", nullable = false, precision = 12, scale = 2)
    @DecimalMin("0.00")
    private BigDecimal shippingCost;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Transient
    public @NonNull BigDecimal getTotal() {
        return getSubtotal().subtract(discountTotal).add(shippingCost);
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderHistoric> historic = new ArrayList<>();


    public enum Status {
        CREATED, PENDING_PAYMENT, PAID, PROCESSING, SHIPPED, DELIVERED, CANCELED, FAILED,
    }

    public enum Types {
        DELIVER, PICKUP,
    }
}
