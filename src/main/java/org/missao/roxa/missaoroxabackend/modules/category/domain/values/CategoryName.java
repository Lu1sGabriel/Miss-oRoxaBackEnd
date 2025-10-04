package org.missao.roxa.missaoroxabackend.modules.category.domain.values;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class CategoryName {

    private final static Pattern REGEX = Pattern.compile(
            "^\\p{L}[\\p{L}'&-]{0,29}(?:\\s\\p{L}[\\p{L}'&-]{0,29})*$"
    );
    private String name;

    public CategoryName(String name) {
        this.name = validate(name);
    }

    private static String validate(String name) {
        if (name == null || StringUtils.isBlank(name)) {
            throw new InvalidRequestDataException("Category name cannot be null or blank.");
        }

        var trimmed = name.trim();

        if (!REGEX.matcher(trimmed).matches()) {
            throw new InvalidRequestDataException("Invalid category name.");
        }

        return trimmed;
    }

    public String getValue() {
        return name;
    }

}