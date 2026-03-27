package com.bodegasfrancisco.authservice.components;

import com.bodegasfrancisco.authservice.model.User;
import com.bodegasfrancisco.authservice.repository.UserRepository;
import com.bodegasfrancisco.authservice.service.JwtService;
import com.bodegasfrancisco.exception.ErrorCodes;
import com.bodegasfrancisco.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.replace("Bearer ", "");
        String userId;
        List<String> roles;

        try {
            userId = jwtService.extractSubject(token);
            var claim = jwtService.extractClaims(token).get("roles");

            if (claim instanceof List<?> list)
                roles = list.stream()
                    .map(Object::toString)
                    .toList();
            else roles = new ArrayList<>();
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (userId == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        userRepository.findById(userId).ifPresent(user -> {
            System.out.println("user found " + user.getUsername());

            if (user.getStatus() == User.Status.PENDING)
                throw new UnauthorizedException(
                    ErrorCodes.USER_NOT_ACTIVATED,
                    "user " + user.getUserId() + " is not activated");
            if (user.getStatus() == User.Status.DELETED)
                throw new UnauthorizedException(
                    ErrorCodes.USER_DELETED,
                    "user " + user.getUserId() + " is deleted");
            
            if (!jwtService.isTokenValid(token, user))
                return;

            var authorities = roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

            var authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

            System.out.println("user logged with " + authorities);

            SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
        });

        filterChain.doFilter(request, response);
    }
}
