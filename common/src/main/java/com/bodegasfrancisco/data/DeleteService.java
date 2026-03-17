package com.bodegasfrancisco.data;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;

public interface DeleteService<ID> {
    void delete(@NonNull ID id) throws BadRequestException;
}
