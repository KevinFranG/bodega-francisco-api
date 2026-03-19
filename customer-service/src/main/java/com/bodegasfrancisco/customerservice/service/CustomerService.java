package com.bodegasfrancisco.customerservice.service;

import com.bodegasfrancisco.customerservice.dto.UpdateCustomerDTO;
import com.bodegasfrancisco.customerservice.mapper.CustomerMapper;
import com.bodegasfrancisco.customerservice.model.Customer;
import com.bodegasfrancisco.customerservice.repository.CustomerRepository;
import com.bodegasfrancisco.data.CreateService;
import com.bodegasfrancisco.data.DeleteService;
import com.bodegasfrancisco.data.IndexService;
import com.bodegasfrancisco.data.UpdateService;
import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.exception.ErrorCodes;
import com.bodegasfrancisco.kafka.events.CustomerCreationRequestedEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor

@Service
public class CustomerService implements
    CreateService<Customer, CustomerCreationRequestedEvent>,
    UpdateService<Customer, UpdateCustomerDTO>,
    IndexService<Customer, ObjectId>,
    DeleteService<ObjectId>
{

    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public Customer findByEmail(@NonNull String email) throws BadRequestException {
        return repository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException(ErrorCodes.USER_NOT_FOUND, "user not found"));
    }

    @Override
    public Customer create(@NonNull CustomerCreationRequestedEvent dto) {
        var customer = mapper.toObject(dto);

        return repository.save(customer);
    }

    @Override
    public void delete(@NonNull ObjectId id) throws BadRequestException {
        var customer = index(id);
        customer.setStatus(Customer.Status.DELETED);

        repository.save(customer);
    }

    @Override
    public Customer update(@NonNull UpdateCustomerDTO dto)
        throws BadRequestException {

        var customer = index(new ObjectId(dto.id()));
        mapper.merge(customer, dto);

        return repository.save(customer);
    }
}
