package org.missao.roxa.missaoroxabackend.core.shared.helper.password;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class PasswordHasher {

    private final Argon2PasswordEncoder encoder;

    public PasswordHasher(
            @Value("${security.argon2.salt-length:16}") int saltLength,
            @Value("${security.argon2.hash-length:32}") int hashLength,
            @Value("${security.argon2.parallelism:1}") int parallelism,
            @Value("${security.argon2.memory:15360}") int memory,
            @Value("${security.argon2.iterations:2}") int iterations
    ) {
        this.encoder = new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    public String hash(String password) {
        return encoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}