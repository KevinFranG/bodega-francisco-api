package com.bodegasfrancisco.web;

import com.bodegasfrancisco.data.DeleteService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeleteEndpoint<ID> {
    DeleteService<ID> getService();


    @DeleteMapping("/{id}")
    default void delete(@PathVariable ID id) throws BadRequestException {
        getService().delete(id);
    }
}
