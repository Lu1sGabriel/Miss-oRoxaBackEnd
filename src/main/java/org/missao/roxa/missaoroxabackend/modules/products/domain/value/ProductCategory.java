package org.missao.roxa.missaoroxabackend.modules.products.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "category")
public final class ProductCategory {
    private String category;

    private static final Pattern REGEX = Pattern.compile(
            "^(?!\\d+$)(?!.* {2,})(?! )[A-Za-zÀ-ÖØ-öø-ÿ0-9 &-]+(?<! )$"
    );

    public ProductCategory(String category) {
        this.category = validate(category);
    }

    private static String validate(String category) {
        if (category == null || StringUtils.isBlank(category)) {
            throw new InvalidRequestDataException("The product category cannot be null or blank.");
        }

        String trimmed = category.trim();

        if (trimmed.length() < 3 || trimmed.length() > 50) {
            throw new InvalidRequestDataException("The product category must be between 3 and 50 characters.");
        }

        if (!REGEX.matcher(trimmed).matches()) {
            throw new InvalidRequestDataException("Invalid product category.");
        }

        return trimmed;
    }

    public String getValue() {
        return category;
    }

}