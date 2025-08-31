package org.missao.roxa.missaoroxabackend.core.configuration;

import jakarta.annotation.PostConstruct;
import org.missao.roxa.missaoroxabackend.core.shared.helper.password.PasswordHasher;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Password;
import org.springframework.stereotype.Component;

@Component
public final class PasswordBootstrapConfiguration {

    private final PasswordHasher passwordHasher;

    public PasswordBootstrapConfiguration(PasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    @PostConstruct
    public void init() {
        Password.injectHasher(passwordHasher);
    }

}