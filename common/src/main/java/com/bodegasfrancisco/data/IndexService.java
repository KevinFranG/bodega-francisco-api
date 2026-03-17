package com.bodegasfrancisco.data;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndexService<T, ID> {
    JpaRepository<T, ID> getRepository();

    default T find(@NonNull ID id) throws BadRequestException {
        return getRepository().findById(id)
            .orElseThrow(() -> new BadRequestException("entity not exists"));
    }

    default List<T> index() {
        return getRepository().findAll();
    }
}
