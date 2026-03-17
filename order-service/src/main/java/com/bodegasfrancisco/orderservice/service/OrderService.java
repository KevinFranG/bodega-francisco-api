package com.bodegasfrancisco.orderservice.service;

import com.bodegasfrancisco.orderservice.model.Order;
import com.bodegasfrancisco.orderservice.model.OrderItem;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    public @Nullable Order create(@NonNull Order order) {
        order.setSubtotal(
            order.getItems().stream()
                .map(i -> i.getUnitPrice()
                    .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        order.setDiscountTotal(
            order.getItems().stream()
                .map(OrderItem::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        return order;
    }

}
