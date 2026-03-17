package com.bodegasfrancisco.orderservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderHistoricDTO {
    private Integer orderId;
}
