package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.exception.ErrorCodes;
import com.bodegasfrancisco.exception.InternalServerException;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class HashService {

    public String sha256(@NonNull String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes());
            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerException(
                ErrorCodes.INVALID_HASH_OPERATION,
                "couldn't encrypt a value"
            );
        }
    }
}
