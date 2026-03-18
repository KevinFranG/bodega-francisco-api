package com.bodegasfrancisco.data;

import com.bodegasfrancisco.exception.BadRequestException;
import lombok.NonNull;

public interface UpdateService<T, DTO> {
    T update(@NonNull DTO dto) throws BadRequestException;
}
