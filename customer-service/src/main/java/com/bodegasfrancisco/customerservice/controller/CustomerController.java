package com.bodegasfrancisco.customerservice.controller;

import com.bodegasfrancisco.customerservice.dto.CreateCustomerDTO;
import com.bodegasfrancisco.customerservice.dto.ResponseCustomerDTO;
import com.bodegasfrancisco.customerservice.dto.UpdateCustomerDTO;
import com.bodegasfrancisco.customerservice.mapper.CustomerMapper;
import com.bodegasfrancisco.customerservice.service.CustomerService;
import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.web.CreateEndpoint;
import com.bodegasfrancisco.web.DeleteEndpoint;
import com.bodegasfrancisco.web.IndexEndpoint;
import com.bodegasfrancisco.web.UpdateEndpoint;
import com.bodegasfrancisco.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping
public class CustomerController implements
    CreateEndpoint<ResponseCustomerDTO, CreateCustomerDTO>,
    UpdateEndpoint<ResponseCustomerDTO, UpdateCustomerDTO>,
    IndexEndpoint<ResponseCustomerDTO, ObjectId>,
    DeleteEndpoint<ObjectId>
{

    private final CustomerService service;
    private final CustomerMapper mapper;


    @Override
    public ApiResponse<ResponseCustomerDTO> create(CreateCustomerDTO dto) {
        return null;
        /*var customer = service.create(dto);

        return ApiResponse.<ResponseCustomerDTO>builder()
            .data(mapper.toResponse(customer))
            .message("customer created")
            .build();*/
    }

    @Override
    public void delete(ObjectId id) throws BadRequestException {
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
    public ApiResponse<ResponseCustomerDTO> index(ObjectId id) {
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
