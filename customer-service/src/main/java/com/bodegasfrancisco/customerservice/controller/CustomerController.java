package com.bodegasfrancisco.customerservice.controller;

import com.bodegasfrancisco.customerservice.dto.ResponseCustomerDTO;
import com.bodegasfrancisco.customerservice.dto.UpdateCustomerDTO;
import com.bodegasfrancisco.customerservice.mapper.CustomerMapper;
import com.bodegasfrancisco.customerservice.service.CustomerService;
import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.web.DeleteEndpoint;
import com.bodegasfrancisco.web.IndexEndpoint;
import com.bodegasfrancisco.web.UpdateEndpoint;
import com.bodegasfrancisco.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping
public class CustomerController implements
    UpdateEndpoint<ResponseCustomerDTO, UpdateCustomerDTO>,
    IndexEndpoint<ResponseCustomerDTO, String>,
    DeleteEndpoint<String>
{

    private final CustomerService service;
    private final CustomerMapper mapper;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) throws BadRequestException {
        service.delete(id);
    }

    @Override
    public ApiResponse<List<ResponseCustomerDTO>> index() {
        return ApiResponse.<List<ResponseCustomerDTO>>builder()
            .data(service.index().stream()
                      .map(mapper::toResponse)
                      .toList())
            .message("customers indexed")
            .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ResponseCustomerDTO> index(String id) {
        var customer = service.index(id);

        return ApiResponse.<ResponseCustomerDTO>builder()
            .data(mapper.toResponse(customer))
            .message("customer found")
            .build();
    }

    @Override
    public ApiResponse<ResponseCustomerDTO> update(UpdateCustomerDTO dto)
        throws BadRequestException {

        var customer = service.update(dto);

        return ApiResponse.<ResponseCustomerDTO>builder()
            .data(mapper.toResponse(customer))
            .message("customer updated")
            .build();
    }
}
