package org.missao.roxa.missaoroxabackend.modules.address.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "neighborhood")
public final class Neighborhood {

    private String neighborhood;

    public Neighborhood(String neighborhood) {
        this.neighborhood = validate(neighborhood);
    }

    private static String validate(String neighborhood) {
        if (neighborhood == null || StringUtils.isBlank(neighborhood)) {
            throw HttpException.badRequest("Neighborhood cannot be null or empty");
        }

        if (neighborhood.length() < 2 || neighborhood.length() > 100) {
            throw HttpException.badRequest("Neighborhood must be between 2 and 100 characters");
        }

        return neighborhood.trim();
    }

    public String getValue() {
        return neighborhood;
    }

}