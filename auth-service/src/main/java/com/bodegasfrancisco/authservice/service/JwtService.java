package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.authservice.config.JwtConfig;
import com.bodegasfrancisco.authservice.model.Role;
import com.bodegasfrancisco.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.jspecify.annotations.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtConfig.Properties properties;

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtService(JwtConfig.Properties properties) {
        this.properties = properties;

        try {
            this.privateKey = readPrivateKey(properties.privateKey());
            this.publicKey = readPublicKey(properties.publicKey());
        } catch (Exception e) {
            throw new IllegalStateException("Could not load RSA keys", e);
        }
    }


    public String generateAccessToken(@NonNull User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(properties.accessExpiration());

        Set<String> roles = user.getRoles()
            .stream()
            .map(Role::getName)
            .map(role -> "ROLE_" + role)
            .collect(Collectors.toSet());

        return Jwts.builder()
            .subject(user.getId())
            .claim("userId", user.getUserId())
            .claim("roles", roles)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(privateKey, Jwts.SIG.RS256)
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
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact();
    }

    public Claims extractClaims(@NonNull String token) {
        return Jwts.parser()
            .verifyWith(publicKey)
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


    private PrivateKey readPrivateKey(@NonNull Resource resource)
        throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String key;
        try (var input = resource.getInputStream()) {
            key = new String(input.readAllBytes(), StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        }

        byte[] decode = Base64.getDecoder().decode(key);
        var spec = new PKCS8EncodedKeySpec(decode);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey readPublicKey(@NonNull Resource resource)
        throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String key;
        try (var input = resource.getInputStream()) {
            key = new String(input.readAllBytes(), StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        }

        byte[] decode = Base64.getDecoder().decode(key);
        var spec = new X509EncodedKeySpec(decode);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
