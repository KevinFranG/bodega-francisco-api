package com.bodegasfrancisco.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "order_historics")
public class OrderHistoric {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Order order;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "previous_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Order.Status previousStatus;

    @Column(name = "new_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Order.Status newStatus;

    @Column(length = 200)
    private String reason;

    @Column(name = "changed_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime changedAt;
}
