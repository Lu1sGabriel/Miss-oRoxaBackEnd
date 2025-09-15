package org.missao.roxa.missaoroxabackend.modules.states.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "uf")
public final class UF {

    private String uf;

    private static final Pattern REGEX = Pattern.compile("^[A-Z]{2}$");

    public UF(String uf) {
        this.uf = validate(uf);
    }

    private static String validate(String uf) {
        if (uf == null || StringUtils.isBlank(uf)) {
            throw new InvalidRequestDataException("UF cannot be null or empty");
        }

        if (!REGEX.matcher(uf).matches()) {
            throw new InvalidRequestDataException("Invalid UF format. Expected two uppercase letters, e.g., SP");
        }

        return uf.trim();
    }

    public String getValue() {
        return uf;
    }

}