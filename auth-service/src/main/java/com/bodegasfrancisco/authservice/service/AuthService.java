package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.authservice.dto.AuthResponseDTO;
import com.bodegasfrancisco.authservice.dto.CreateAdminDTO;
import com.bodegasfrancisco.authservice.dto.CredentialsDTO;
import com.bodegasfrancisco.authservice.dto.CreateCustomerDTO;
import com.bodegasfrancisco.authservice.mapper.UserMapper;
import com.bodegasfrancisco.authservice.model.User;
import com.bodegasfrancisco.exception.ErrorCodes;
import com.bodegasfrancisco.exception.UnauthorizedException;
import com.bodegasfrancisco.kafka.events.CustomerCreationRequestedEvent;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@RequiredArgsConstructor

@Service
public class AuthService {

    private final UserService service;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final HashService hashService;
    private final UserMapper mapper;
    private final AuthEventProducer producer;


    public AuthResponseDTO login(@NonNull CredentialsDTO credential) {
        User user = service.findByEmail(credential.email());

        if (!passwordEncoder.matches(credential.password(), user.getPassword()))
            throw new UnauthorizedException(
                ErrorCodes.INVALID_CREDENTIALS,
                "invalid user or password");

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponseDTO(
            accessToken,
            refreshToken,
            "Bearer",
            jwtService.expirationTime() / 1000
        );
    }

    public AuthResponseDTO register(@NonNull CreateCustomerDTO dto) {
        var user = mapper.toObject(dto);
        user.setType(User.Roles.CUSTOMER);
        user.setStatus(User.Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        service.create(user);

        producer.sendEvent(
            new CustomerCreationRequestedEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                dto.name(),
                dto.lastname(),
                dto.email(),
                dto.phone()
            )
        );

        return generateResponse(user);
    }

    public AuthResponseDTO register(@NonNull CreateAdminDTO dto) {
        var user = mapper.toObject(dto);
        user.setUserId(UUID.randomUUID().toString());
        user.setType(User.Roles.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = service.create(user);

        return generateResponse(user);
    }


    private @NonNull AuthResponseDTO generateResponse(@NonNull User user) {
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponseDTO(
            accessToken,
            refreshToken,
            "Bearer",
            jwtService.expirationTime() / 1000
        );
    }
}
