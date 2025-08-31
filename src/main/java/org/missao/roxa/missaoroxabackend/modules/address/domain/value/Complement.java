package org.missao.roxa.missaoroxabackend.modules.address.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "complement")
public final class Complement {

    private String complement;

    public Complement(String complement) {
        this.complement = validate(complement);
    }

    private static String validate(String complement) {
        if (complement.length() > 100 || StringUtils.isBlank(complement)) {
            throw HttpException.badRequest("Complement must be up to 100 characters and must not be empty.");
        }

        return complement.trim();
    }

    public String getValue() {
        return complement;
    }

}