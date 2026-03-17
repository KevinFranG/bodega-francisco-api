package com.bodegasfrancisco.orderservice.mapper;

import com.bodegasfrancisco.orderservice.dto.CreateOrderItemDTO;
import com.bodegasfrancisco.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderItemMapper {

    OrderItem toEntity(CreateOrderItemDTO dto);
}
