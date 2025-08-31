package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.core.shared.helper.password.PasswordHasher;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "password")
public final class Password {

    private static final Pattern REGEX = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\sÇç])\\S+$"
    );

    private static PasswordHasher hashUtil;

    private String password;

    public Password(String rawPassword) {
        this.password = hash(validate(rawPassword));
    }

    private static String hash(String password) {
        if (hashUtil == null) throw new IllegalStateException("The password hashing utility has not been initialized.");
        return hashUtil.hash(password);
    }

    private static String validate(String password) {
        if (password == null || StringUtils.isBlank(password)) {
            throw HttpException.badRequest("Please cannot be null or blank.");
        }

        if (password.length() < 8) {
            throw HttpException.badRequest("Password must be at least 8 characters long.");
        }

        if (password.toLowerCase().contains("ç")) {
            throw HttpException.badRequest("Password cannot contain the character 'ç' or 'Ç'.");
        }

        if (!REGEX.matcher(password).matches()) {
            throw HttpException.badRequest("Password must include at least one uppercase letter, one lowercase letter, one number, and one special character.");
        }

        return password;
    }

    public static void injectHasher(PasswordHasher util) {
        if (Password.hashUtil != null) {
            throw new IllegalStateException("The password hashing utility has already been initialized.");
        }
        Password.hashUtil = util;
    }

    public void isPasswordMatches(String encodedPassword, String rawPassword) {
        boolean matches = hashUtil.matches(rawPassword, encodedPassword);
        if (!matches) {
            throw HttpException.badRequest("The current password you entered is incorrect.");
        }
    }

    public String getValue() {
        return password;
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }

}