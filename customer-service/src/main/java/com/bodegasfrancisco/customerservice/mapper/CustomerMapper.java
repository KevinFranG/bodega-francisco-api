package com.bodegasfrancisco.customerservice.mapper;

import com.bodegasfrancisco.customerservice.dto.*;
import com.bodegasfrancisco.customerservice.model.CartItem;
import com.bodegasfrancisco.customerservice.model.Customer;
import com.bodegasfrancisco.customerservice.model.CustomerAddress;
import com.bodegasfrancisco.customerservice.model.CustomerCard;
import com.bodegasfrancisco.kafka.events.CustomerCreationRequestedEvent;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer toObject(CustomerCreationRequestedEvent dto);

    ResponseCustomerDTO toResponse(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void merge(@MappingTarget Customer customer, UpdateCustomerDTO dto);



    CustomerCard toObject(CreateCustomerCardDTO dto);

    ResponseCustomerCardDTO toResponse(CustomerCard card);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void merge(@MappingTarget CustomerCard card, UpdateCustomerCardDTO dto);



    CustomerAddress toObject(CreateCustomerAddressDTO dto);

    ResponseCustomerAddressDTO toResponse(CustomerAddress address);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void merge(@MappingTarget CustomerAddress address, UpdateCustomerAddressDTO dto);



    CartItem toObject(CreateCartItemDTO dto);

    ResponseCartItemDTO toResponse(CartItem cartItem);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void merge(@MappingTarget CartItem cartItem, UpdateCartItemDTO dto);
}
