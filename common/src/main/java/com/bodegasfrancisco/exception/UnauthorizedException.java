package com.bodegasfrancisco.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(ErrorCodes errorCode, String message) {
        super(HttpStatus.UNAUTHORIZED, errorCode, message);
    }
}
