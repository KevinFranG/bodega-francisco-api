package com.bodegasfrancisco.web.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
