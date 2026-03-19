package com.bodegasfrancisco.authservice.mapper;

import com.bodegasfrancisco.authservice.dto.CreateAdminDTO;
import com.bodegasfrancisco.authservice.dto.CreateCustomerDTO;
import com.bodegasfrancisco.authservice.dto.ResponseUserDTO;
import com.bodegasfrancisco.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toObject(CreateCustomerDTO dto);

    ResponseUserDTO toResponse(User user);



    User toObject(CreateAdminDTO dto);
}
