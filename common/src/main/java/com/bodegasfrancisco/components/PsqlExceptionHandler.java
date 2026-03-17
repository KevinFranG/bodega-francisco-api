package com.bodegasfrancisco.components;

import com.bodegasfrancisco.web.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface PsqlExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    default ResponseEntity<ApiErrorResponse> handleDuplicateKey(
        @NonNull HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiErrorResponse.builder()
                      .reason("DUPLICATE_KEY")
                      .message("A record with the same unique value already exists")
                      .status(HttpStatus.CONFLICT.value())
                      .path(request.getRequestURI())
                      .build()
            );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    default ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
        DataIntegrityViolationException ex,
        HttpServletRequest request
    ) {
        PSQLException psqlEx = findCause(ex);

        if (psqlEx != null)
            return handlePostgresIntegrity(psqlEx, request);

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                ApiErrorResponse.builder()
                    .reason("data integrity violated")
                    .message("The operation violates a database integrity rule")
                    .status(HttpStatus.CONFLICT.value())
                    .path(request.getRequestURI())
                    .build()
            );
    }

    @SuppressWarnings("unchecked")
    private <T extends Throwable> @Nullable T findCause(Throwable ex) {
        Throwable current = ex;
        while (current != null) {
            if (current instanceof PSQLException) {
                return (T) current;
            }
            current = current.getCause();
        }
        return null;
    }

    private @NonNull ResponseEntity<ApiErrorResponse> handlePostgresIntegrity(
        @NonNull PSQLException ex,
        @NonNull HttpServletRequest request
    ) {
        String sqlState = ex.getSQLState();

        var builder = ApiErrorResponse.builder()
            .path(request.getRequestURI());

        return switch (sqlState) {
            case "23505" -> ResponseEntity.status(HttpStatus.CONFLICT).body(
                builder
                    .reason("duplicated unique value")
                    .message("a unique value already exists")
                    .status(HttpStatus.CONFLICT.value())
                    .build()
            );

            case "23503" -> ResponseEntity.status(HttpStatus.CONFLICT).body(
                builder
                    .reason("invalid entity references")
                    .message("referenced data does not exist or is still in use")
                    .status(HttpStatus.CONFLICT.value())
                    .build()
            );

            case "23502" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                builder
                    .reason("invalid null value")
                    .message("a required field is missing")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build()
            );

            case "23514" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                builder
                    .reason("invalid data format")
                    .message("a database rule was violated")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build()
            );

            default -> ResponseEntity.status(HttpStatus.CONFLICT).body(
                builder
                    .reason("data integrity violated")
                    .message("the operation violates a integrity rule")
                    .status(HttpStatus.CONFLICT.value())
                    .build()
            );
        };
    }
}
