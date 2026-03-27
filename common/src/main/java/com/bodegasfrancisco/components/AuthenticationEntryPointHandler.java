package com.bodegasfrancisco.components;

import com.bodegasfrancisco.web.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper json = new ObjectMapper();


    @Override
    public void commence(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull AuthenticationException authException
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        var details = new LinkedHashMap<String, String>();
        details.put("message", authException.getMessage());

        response.getWriter().write(json.writeValueAsString(
            ApiErrorResponse.builder()
                .path(request.getRequestURI())
                .reason("couldn't read bearer token")
                .message("unauthorized access to request")
                .details(details)
                .status(401)
                .build()
        ));
    }
}
