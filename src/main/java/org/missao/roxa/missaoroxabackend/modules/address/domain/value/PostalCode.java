package org.missao.roxa.missaoroxabackend.modules.address.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "postalCode")
public final class PostalCode {

    private String postalCode;

    private static final Pattern REGEX = Pattern.compile("^\\d{8}$");

    public PostalCode(String postalCode) {
        this.postalCode = validate(postalCode);
    }

    private static String validate(String postalCode) {
        if (postalCode == null || StringUtils.isBlank(postalCode)) {
            throw HttpException.badRequest("Postal code cannot be null or empty");
        }

        if (!REGEX.matcher(postalCode).matches()) {
            throw HttpException.badRequest("Invalid postal code format. Expected: 8 digits, only numbers (e.g., 12345678)");
        }

        return postalCode.trim();
    }

    public String getValue() {
        return postalCode;
    }

}