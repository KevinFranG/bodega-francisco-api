package com.bodegasfrancisco.components;

import com.bodegasfrancisco.web.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
        @NonNull MethodArgumentNotValidException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, String> validations = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validations.put(error.getField(), error.getDefaultMessage());
        }

        String path = request instanceof ServletWebRequest swr
            ? swr.getRequest().getRequestURI()
            : null;

        return ResponseEntity
            .badRequest()
            .body(
                ApiErrorResponse.builder()
                    .message("validation failed")
                    .status(status.value())
                    .path(path)
                    .details(validations)
                    .build()
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
        @NonNull HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("unexpected error")
                    .path(request.getRequestURI())
                    .build()
            );
    }
}
