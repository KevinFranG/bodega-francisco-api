package com.bodegasfrancisco.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableConfigurationProperties(JwtConfig.Properties.class)
public class JwtConfig {

    @ConfigurationProperties(prefix = "security.jwt")
    public record Properties(
        Resource privateKey,
        Resource publicKey,
        long accessExpiration,
        long refreshExpiration
    ) {}
}
