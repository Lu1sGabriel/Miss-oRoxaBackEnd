package org.missao.roxa.missaoroxabackend.modules.municipality.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public final class MunicipalityName {

    private String name;

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 100;
    private static final Pattern REGEX = Pattern.compile("^[A-Za-zÀ-ÿ'\\-\\s]+$");

    public MunicipalityName(String name) {
        this.name = validate(name);
    }

    private static String validate(String name) {
        if (name == null || StringUtils.isBlank(name)) {
            throw new InvalidRequestDataException("Municipality name cannot be null or empty");
        }

        String trimmed = name.trim();

        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            throw new InvalidRequestDataException(
                    String.format("Municipality name must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH)
            );
        }

        if (!REGEX.matcher(trimmed).matches()) {
            throw new InvalidRequestDataException(
                    "Municipality name contains invalid characters. Only letters, spaces, apostrophes and hyphens are allowed"
            );
        }

        return trimmed;
    }

    public String getValue() {
        return name;
    }

}