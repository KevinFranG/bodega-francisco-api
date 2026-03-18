package com.bodegasfrancisco.data;

import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.exception.ErrorCodes;
import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IndexService<T, ID> {
    ListCrudRepository<T, ID> getRepository();

    default T index(@NonNull ID id) throws BadRequestException {
        return getRepository().findById(id)
            .orElseThrow(() -> new BadRequestException(
                ErrorCodes.ENTITY_NOT_FOUND,
                "entity not exists")
            );
    }

    default List<T> index() {
        return getRepository().findAll();
    }
}
