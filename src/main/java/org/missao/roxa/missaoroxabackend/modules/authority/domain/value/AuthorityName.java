package org.missao.roxa.missaoroxabackend.modules.authority.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public final class AuthorityName {

    private final static Pattern REGEX = Pattern.compile("^[a-z]+:[a-z]{2,}(?:-[a-z]{2,})*$");

    private String name;

    public AuthorityName(String value) {
        this.name = validate(value);
    }

    private static String validate(String value) {
        if (value == null || StringUtils.isBlank(value)) {
            throw new InvalidRequestDataException("Authority name cannot be null or empty.");
        }

        if (!REGEX.matcher(value).matches()) {
            throw new InvalidRequestDataException("Invalid authority name.");
        }

        return value;
    }

    public String getValue() {
        return name;
    }

}