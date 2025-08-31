package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "number")
public final class PhoneNumber {

    private String number;

    private static final Pattern REGEX = Pattern.compile("^\\d{1,3}\\d{2}\\d{8,9}$");

    public PhoneNumber(String number) {
        this.number = validate(number);
    }

    private static String validate(String number) {
        if (number == null || StringUtils.isBlank(number)) {
            throw HttpException.badRequest("Phone number cannot be null or empty");
        }

        if (!REGEX.matcher(number).matches()) {
            throw HttpException.badRequest("The phone number format is invalid. Expected: country code + DDD + number, only digits.");
        }

        return number.trim();
    }

    public String getValue() {
        return number;
    }

}