package com.bodegasfrancisco.orderservice.mapper;

import com.bodegasfrancisco.orderservice.dto.CreateOrderDTO;
import com.bodegasfrancisco.orderservice.model.Order;
import org.jspecify.annotations.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = OrderItemMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    Order toEntity(CreateOrderDTO dto);

    @AfterMapping
    default void link(@MappingTarget @NonNull Order order) {
        if (order.getItems() == null) return;
        order.getItems().forEach(item -> item.setOrder(order));
    }
}
