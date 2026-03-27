package com.bodegasfrancisco.components;

import com.bodegasfrancisco.web.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;

@RequiredArgsConstructor

@Component
public class AccessDeniedEntryPointHandler implements AccessDeniedHandler {

    private final ObjectMapper json = new ObjectMapper();


    @Override
    public void handle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull AccessDeniedException accessDeniedException
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        var details = new LinkedHashMap<String, String>();
        details.put("message", accessDeniedException.getMessage());

        response.getWriter().write(json.writeValueAsString(
            ApiErrorResponse.builder()
                .path(request.getRequestURI())
                .reason("user hasn't enough rights")
                .message("unauthorized access to request")
                .details(details)
                .status(403)
                .build()
        ));

    }
}
