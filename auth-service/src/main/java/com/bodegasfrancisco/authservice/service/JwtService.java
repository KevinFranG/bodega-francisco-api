package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.authservice.config.JwtConfig;
import com.bodegasfrancisco.authservice.model.Role;
import com.bodegasfrancisco.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtConfig.Properties properties;
    private final SecretKey secretKey;

    public JwtService(JwtConfig.Properties properties) {
        this.properties = properties;

        this.secretKey = Keys.hmacShaKeyFor(
            properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }


    public String generateAccessToken(@NonNull User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(properties.accessExpiration());

        Set<String> roles = user.getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.toSet());
        roles.add(user.getType().name());

        return Jwts.builder()
            .subject(user.getId())
            .claim("userId", user.getUserId())
            .claim("roles", roles)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(secretKey)
            .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(properties.refreshExpiration());

        return Jwts.builder()
            .subject(user.getId())
            .claim("type", "refresh")
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(secretKey)
            .compact();
    }

    public Claims extractClaims(@NonNull String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String extractSubject(@NonNull String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(@NonNull String token, @NonNull User user) {
        String subject = extractSubject(token);
        return subject.equals(user.getId()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        var expiration = extractClaims(token).getExpiration();
        return expiration.before(new java.util.Date());
    }

    public long expirationTime() {
        return properties.refreshExpiration();
    }
}
