package com.bodegasfrancisco.customerservice.dto;

import com.bodegasfrancisco.customerservice.model.Customer;

import java.util.List;

public record ResponseCustomerDTO(
    String id,
    String name,
    String lastname,
    String email,
    String phone,
    Customer.Status status,
    List<ResponseCartItemDTO> cart,
    List<ResponseCustomerAddressDTO> addresses,
    List<ResponseCustomerCardDTO> cards
) {
}
