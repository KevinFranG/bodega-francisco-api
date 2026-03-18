package com.bodegasfrancisco.components;

import com.bodegasfrancisco.exception.ApiException;
import com.bodegasfrancisco.web.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    protected CommonExceptionHandler() {}

    private String resolvePath(WebRequest request) {
        return request instanceof ServletWebRequest swr
            ? swr.getRequest().getRequestURI()
            : null;
    }

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

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("invalid request")
                    .message("validation failed")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(validations)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(
        @NonNull HttpMessageNotReadableException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        String message = "request body is invalid";

        if (ex.getMessage() != null) {
            if (ex.getMessage().contains("Required request body is missing")) {
                message = "request body is required";
            } else if (ex.getMessage().contains("JSON parse error")) {
                message = "malformed json body";
            }
        }

        ex.getMostSpecificCause();
        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("invalid request body")
                    .message(message)
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(ex.getMostSpecificCause().getMessage())
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingServletRequestParameter(
        @NonNull MissingServletRequestParameterException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("parameter", ex.getParameterName());
        details.put("expectedType", ex.getParameterType());

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("missing request parameter")
                    .message("required request parameter is missing")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingPathVariable(
        @NonNull MissingPathVariableException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("pathVariable", ex.getVariableName());

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("missing path variable")
                    .message("required path variable is missing")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleServletRequestBindingException(
        @NonNull ServletRequestBindingException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        String reason = "request binding error";
        String message = "request binding failed";
        Map<String, Object> details = new LinkedHashMap<>();

        if (ex instanceof MissingRequestHeaderException headerEx) {
            reason = "missing request header";
            message = "required request header is missing";
            details.put("header", headerEx.getHeaderName());
        } else {
            details.put("error", ex.getMessage());
        }

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason(reason)
                    .message(message)
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingServletRequestPart(
        @NonNull MissingServletRequestPartException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("part", ex.getRequestPartName());

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("missing request part")
                    .message("required multipart request part is missing")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        @NonNull HttpRequestMethodNotSupportedException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("method", ex.getMethod());
        details.put("supportedMethods", ex.getSupportedMethods());

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("method not allowed")
                    .message("http method not supported for this endpoint")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        @NonNull HttpMediaTypeNotSupportedException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("contentType", ex.getContentType());
        details.put("supportedMediaTypes", ex.getSupportedMediaTypes());

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("unsupported media type")
                    .message("content type is not supported")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleNoHandlerFoundException(
        @NonNull NoHandlerFoundException ex,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("method", ex.getHttpMethod());

        return ResponseEntity
            .status(status.value())
            .body(
                ApiErrorResponse.builder()
                    .reason("resource not found")
                    .message("endpoint not found")
                    .status(status.value())
                    .path(resolvePath(request))
                    .details(details)
                    .build()
            );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
        @NonNull MethodArgumentTypeMismatchException ex,
        @NonNull HttpServletRequest request
    ) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("parameter", ex.getName());
        details.put("value", ex.getValue());
        details.put(
            "expectedType",
            ex.getRequiredType() != null
                ? ex.getRequiredType().getSimpleName()
                : null
        );

        return ResponseEntity
            .badRequest()
            .body(
                ApiErrorResponse.builder()
                    .reason("invalid parameter type")
                    .message("request parameter has an invalid type")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .path(request.getRequestURI())
                    .details(details)
                    .build()
            );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
        @NonNull ApiException ex,
        @NonNull HttpServletRequest request
    ) {
        return ResponseEntity
            .status(ex.getStatus())
            .body(
                ApiErrorResponse.builder()
                    .reason(ex.getErrorCode().getReason())
                    .message(ex.getMessage())
                    .status(ex.getStatus().value())
                    .path(request.getRequestURI())
                    .build()
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
        @NonNull Exception ex,
        @NonNull HttpServletRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiErrorResponse.builder()
                    .reason("internal server error")
                    .message("unexpected error")
                    .status(500)
                    .path(request.getRequestURI())
                    .details(ex.getClass().getSimpleName())
                    .build()
            );
    }
}