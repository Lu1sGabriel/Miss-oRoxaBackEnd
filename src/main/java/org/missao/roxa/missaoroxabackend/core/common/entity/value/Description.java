package org.missao.roxa.missaoroxabackend.core.common.entity.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "description")
public class Description {

    private String description;

    private static final Pattern REGEX = Pattern.compile(
            "^(?!.* {2,})(?! )[A-Za-zÀ-ÖØ-öø-ÿ0-9.,!?:;()\"'\\-\\n\\r ]+(?<! )$"
    );

    public Description(String description) {
        this.description = validate(description);
    }

    private static String validate(String description) {
        if (description == null || StringUtils.isBlank(description)) {
            throw new InvalidRequestDataException("The description cannot be null or blank.");
        }

        String trimmed = description.trim();

        if (trimmed.length() < 10 || trimmed.length() > 1000) {
            throw new InvalidRequestDataException("The description must be between 10 and 1000 characters.");
        }

        if (!REGEX.matcher(trimmed).matches()) {
            throw new InvalidRequestDataException("Invalid description.");
        }

        return trimmed;
    }

    public String getValue() {
        return description;
    }

}