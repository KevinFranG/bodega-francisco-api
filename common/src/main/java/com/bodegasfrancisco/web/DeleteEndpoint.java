package com.bodegasfrancisco.web;

import com.bodegasfrancisco.exception.BadRequestException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeleteEndpoint<DeleteID> {

    @DeleteMapping("/{id}")
    void delete(@PathVariable DeleteID id)
        throws BadRequestException;
}
