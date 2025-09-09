package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.util.List;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "number")
public final class PhoneNumber {

    private String number;

    // Lista de todos os DDDs válidos no Brasil
    private static final List<String> VALID_DDDS = List.of(
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "24", "27", "28", "31", "32",
            "33", "34", "35", "37", "38", "41", "42", "43", "44", "45", "46", "47", "48", "49", "51", "53", "54", "55",
            "61", "62", "64", "63", "65", "66", "67", "68", "69", "71", "73", "74", "75", "77", "79", "81", "87", "82",
            "83", "84", "85", "88", "86", "89", "91", "93", "94", "92", "97", "95", "96", "98", "99"
    );

    private static final Pattern REGEX = Pattern.compile("^\\d{11}$");

    public PhoneNumber(String number) {
        this.number = validate(number);
    }

    private static String validate(String number) {
        if (number == null || StringUtils.isBlank(number)) {
            throw HttpException.badRequest("Phone number cannot be null or empty.");
        }

        if (number.length() != 11) {
            throw HttpException.badRequest("Brazilian phone number must have exactly 11 digits: DDD (2) + number (9).");
        }

        if (!REGEX.matcher(number).matches()) {
            throw HttpException.badRequest("Phone number must contain only digits.");
        }

        String ddd = number.substring(0, 2);
        if (!VALID_DDDS.contains(ddd)) {
            throw HttpException.badRequest("Invalid DDD for Brazil: " + ddd);
        }

        return number.trim();
    }

    public String getValue() {
        return number;
    }

}