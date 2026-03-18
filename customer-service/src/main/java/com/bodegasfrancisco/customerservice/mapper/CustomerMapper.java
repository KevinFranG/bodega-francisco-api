package com.bodegasfrancisco.customerservice.mapper;

import com.bodegasfrancisco.customerservice.dto.*;
import com.bodegasfrancisco.customerservice.model.CartItem;
import com.bodegasfrancisco.customerservice.model.Customer;
import com.bodegasfrancisco.customerservice.model.CustomerAddress;
import com.bodegasfrancisco.customerservice.model.CustomerCard;
import org.bson.types.ObjectId;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer toObject(CreateCustomerDTO dto);

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



    default ObjectId map(String id) {
        return id == null ? null : new ObjectId(id);
    }

    default String map(ObjectId id) {
        return id == null ? null : id.toString();
    }
}
