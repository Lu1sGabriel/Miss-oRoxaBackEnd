package org.missao.roxa.missaoroxabackend.modules.products.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "name")
public final class ProductName {

    private final String name;

    private static final Pattern REGEX = Pattern.compile(
            "^(?!\\d+$)(?!.* {2,})(?! )[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,&/_-]+(?<! )$"
    );

    public ProductName(String name) {
        this.name = validate(name);
    }

    private static String validate(String name) {
        if (name == null || StringUtils.isBlank(name)) {
            throw new InvalidRequestDataException("The product name cannot be null or blank.");
        }

        String trimmed = name.trim();

        if (trimmed.length() < 3 || trimmed.length() > 100) {
            throw new InvalidRequestDataException("The product name must be between 3 and 100 characters.");
        }

        if (!REGEX.matcher(trimmed).matches()) {
            throw new InvalidRequestDataException("Invalid product name.");
        }

        return trimmed;
    }

    public String getValue() {
        return name;
    }

}