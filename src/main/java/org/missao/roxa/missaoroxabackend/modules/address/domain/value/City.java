package org.missao.roxa.missaoroxabackend.modules.address.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "city")
public final class City {

    private String city;

    public City(String city) {
        this.city = validate(city);
    }

    private static String validate(String city) {
        if (city == null || StringUtils.isBlank(city)) {
            throw HttpException.badRequest("City cannot be null or empty");
        }

        if (city.length() < 2 || city.length() > 100) {
            throw HttpException.badRequest("City must be between 2 and 100 characters");
        }

        return city.trim();
    }

    public String getValue() {
        return city;
    }

}