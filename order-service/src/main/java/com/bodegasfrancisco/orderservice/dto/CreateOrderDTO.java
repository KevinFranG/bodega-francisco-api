package com.bodegasfrancisco.orderservice.dto;

import com.bodegasfrancisco.orderservice.model.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID attendedEmployeeId;

    @NotNull
    private Order.Types types;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    private BigDecimal shippingCost;

    @NotEmpty
    private List<@NotNull @Valid CreateOrderItemDTO> items;
}
