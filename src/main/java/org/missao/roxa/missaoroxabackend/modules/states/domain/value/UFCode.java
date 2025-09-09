package org.missao.roxa.missaoroxabackend.modules.states.domain.value;


import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
public final class UFCode {

    private String code;
    private static final Pattern CODE_REGEX = Pattern.compile("^\\d{2}$");

    public UFCode(String code) {
        this.code = validate(code);
    }

    private static String validate(String code) {
        if (code == null || StringUtils.isBlank(code)) {
            throw HttpException.badRequest("UF code cannot be null or empty");
        }
        if (!CODE_REGEX.matcher(code).matches()) {
            throw HttpException.badRequest("Invalid UF code format. Expected two digits, e.g., 35");
        }
        return code.trim();
    }

    public String getValue() {
        return code;
    }

}