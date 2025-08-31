package org.missao.roxa.missaoroxabackend.modules.user.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

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

    private static String validate(String value) {
        if (value == null || StringUtils.isBlank(value)) {
            throw HttpException.badRequest("Full name cannot be null or blank.");
        }

        if (!REGEX.matcher(value).matches()) {
            throw HttpException.badRequest("Invalid full name.");
        }

        return value.strip();
    }

    public String getValue() {
        return fullName;
    }

}