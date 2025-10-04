package org.missao.roxa.missaoroxabackend.modules.user.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "fullName")
public final class FullName {

    private static final Pattern REGEX = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ]{2,}(?:[-' ][A-Za-zÀ-ÖØ-öø-ÿ]{2,})+$");

    private String fullName;

    public FullName(String fullName) {
        this.fullName = validate(fullName);
    }

    private static String validate(String fullName) {
        if (fullName == null || StringUtils.isBlank(fullName)) {
            throw new InvalidRequestDataException("Full name cannot be null or blank.");
        }

        var trimmed = fullName.trim();

        if (!REGEX.matcher(trimmed).matches()) {
            throw new InvalidRequestDataException("Invalid full name.");
        }

        return trimmed;
    }

    public String getValue() {
        return fullName;
    }

}