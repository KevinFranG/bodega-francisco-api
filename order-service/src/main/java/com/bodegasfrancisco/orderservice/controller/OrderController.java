package com.bodegasfrancisco.orderservice.controller;

import com.bodegasfrancisco.orderservice.dto.CreateOrderDTO;
import com.bodegasfrancisco.orderservice.mapper.OrderMapper;
import com.bodegasfrancisco.orderservice.model.Order;
import com.bodegasfrancisco.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    static private final List<Order> orders = new ArrayList<>();

    private final OrderMapper mapper;
    private final OrderService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOne(@Valid @RequestBody CreateOrderDTO dto) {
        var order = service.create(mapper.toEntity(dto));
        orders.add(order);
        return order;
    }

    @GetMapping
    public List<Order> getAll() {
        return orders;
    }
}
