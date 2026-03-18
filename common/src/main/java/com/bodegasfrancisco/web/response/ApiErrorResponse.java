package com.bodegasfrancisco.web.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {

    @Builder.Default
    private boolean success = false;

    private String message;

    @Builder.Default
    private String reason = "unknown";

    private int status;

    private String path;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private Object details;
}
