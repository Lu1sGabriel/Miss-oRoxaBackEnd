package org.missao.roxa.missaoroxabackend.modules.authority.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "description")
public final class AuthorityDescription {

    private static final Pattern REGEX = Pattern.compile("^[\\wÀ-ÿ .,;:'\"()\\-]{3,255}$");

    private String description;

    public AuthorityDescription(String description) {
        this.description = validate(description);
    }

    private static String validate(String value) {
        if (value == null || StringUtils.isBlank(value)) {
            throw new InvalidRequestDataException("Authority description cannot be null or blank.");
        }

        if (!REGEX.matcher(value).matches()) {
            throw new InvalidRequestDataException("Invalid authority description.");
        }

        return value;
    }

    public String getValue() {
        return description;
    }

}