package org.missao.roxa.missaoroxabackend.modules.address.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "street")
public final class Street {

    private String street;

    public Street(String street) {
        this.street = validate(street);
    }

    private static String validate(String street) {

        if (street == null || StringUtils.isBlank(street)) {
            throw HttpException.badRequest("Street cannot be null or empty");
        }

        if (street.length() < 3 || street.length() > 255) {
            throw HttpException.badRequest("Street must be between 3 and 255 characters");
        }

        return street.trim();
    }

    public String getValue() {
        return street;
    }

}