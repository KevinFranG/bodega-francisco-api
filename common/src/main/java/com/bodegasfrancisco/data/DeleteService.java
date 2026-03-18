package com.bodegasfrancisco.data;

import com.bodegasfrancisco.exception.BadRequestException;
import lombok.NonNull;

public interface DeleteService<ID> {
    void delete(@NonNull ID id) throws BadRequestException;
}
