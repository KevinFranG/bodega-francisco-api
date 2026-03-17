package com.bodegasfrancisco.data;

import lombok.NonNull;

public interface CreateService<T, DTO> {
    T create(@NonNull DTO dto);
}
