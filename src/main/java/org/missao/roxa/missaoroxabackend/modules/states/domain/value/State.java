package org.missao.roxa.missaoroxabackend.modules.states.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public final class State {

    private String name;

    private static final Pattern REGEX = Pattern.compile("^[A-Z]{2}$");

    public State(String name) {
        this.name = validate(name);
    }

    private static String validate(String state) {
        if (state == null || StringUtils.isBlank(state)) {
            throw new InvalidRequestDataException("State name cannot be null or empty");
        }

        if (!REGEX.matcher(state).matches()) {
            throw new InvalidRequestDataException("Invalid state name format. Expected two uppercase letters, e.g., SP");
        }

        return state.trim();
    }

    public String getValue() {
        return name;
    }

}