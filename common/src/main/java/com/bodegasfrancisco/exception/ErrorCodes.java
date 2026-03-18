package com.bodegasfrancisco.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCodes {
    ENTITY_NOT_FOUND("entity not found");

    private final String reason;
}
