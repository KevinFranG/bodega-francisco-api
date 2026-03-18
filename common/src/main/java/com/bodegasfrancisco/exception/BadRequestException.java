package com.bodegasfrancisco.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    public BadRequestException(ErrorCodes errorCode, String message) {
        super(HttpStatus.BAD_REQUEST, errorCode, message);
    }
}
