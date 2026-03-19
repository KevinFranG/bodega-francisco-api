package com.bodegasfrancisco.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCodes {
    ENTITY_NOT_FOUND("entity not found"),
    USER_NOT_FOUND("user not registered"),
    USER_ALREADY_EXISTS("user email already exists"),
    INVALID_CREDENTIALS("invalid credentials"),
    INVALID_HASH_OPERATION("can not complete hash operation");

    private final String reason;
}
