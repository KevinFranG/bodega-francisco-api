package com.bodegasfrancisco.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCodes errorCode;

    public ApiException(
        @NonNull HttpStatus status,
        @NonNull ErrorCodes errorCode,
        @NonNull String message
    ) {
        super(message);

        this.status = status;
        this.errorCode = errorCode;
    }
}
