package com.experiment.departamentopessoal.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
public class JwtKeyProvider {

    private final SecretKey key;

    public JwtKeyProvider(@Value("${app.security.jwt.secret:}") String secret) {
        if (secret == null || secret.isBlank()) {
            byte[] bytes = new byte[32];
            new SecureRandom().nextBytes(bytes);
            this.key = new SecretKeySpec(bytes, "HmacSHA256");
            return;
        }
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("app.security.jwt.secret must have at least 32 bytes");
        }
        this.key = new SecretKeySpec(bytes, "HmacSHA256");
    }

    public SecretKey getKey() {
        return key;
    }
}
