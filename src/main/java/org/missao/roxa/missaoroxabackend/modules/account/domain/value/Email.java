package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
public final class Email {
    private String email;

    private static final Pattern REGEX = Pattern.compile(
            "^(?=.{1,254}$)(?=.{1,64}@)[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" +
                    "(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
                    "([a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+" +
                    "[a-zA-Z]{2,}$"
    );

    public Email(String email) {
        this.email = validate(email);
    }

    private static String validate(String email) {
        if (email == null || StringUtils.isBlank(email)) {
            throw new InvalidRequestDataException("Email cannot be null or empty");
        }
        if (!REGEX.matcher(email).matches()) {
            throw new InvalidRequestDataException("The email address format is invalid.");
        }
        return email.trim();
    }

    public String getValue() {
        return email;
    }

}