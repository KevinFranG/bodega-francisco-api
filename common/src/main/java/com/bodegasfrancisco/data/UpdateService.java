package com.bodegasfrancisco.data;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;

public interface UpdateService<T, DTO> {
    T update(@NonNull DTO dto) throws BadRequestException;
}
