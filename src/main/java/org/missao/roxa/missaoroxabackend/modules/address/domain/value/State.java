package org.missao.roxa.missaoroxabackend.modules.address.domain.value;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "state")
public final class State {

    private String state;

    private static final Pattern REGEX = Pattern.compile("^[A-Z]{2}$");

    public State(String state) {
        this.state = validate(state);
    }

    private static String validate(String state) {
        if (state == null || StringUtils.isBlank(state)) {
            throw HttpException.badRequest("State cannot be null or empty");
        }

        if (!REGEX.matcher(state).matches()) {
            throw HttpException.badRequest("Invalid state format. Expected two uppercase letters, e.g., SP");
        }

        return state.trim();
    }

    public String getValue() {
        return state;
    }

}