package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "level")
public final class Level {

    private static final byte MAX_LEVEL = 100;

    private byte level;

    public Level(byte level) {
        this.level = validate(level);
    }

    private static byte validate(int level) {
        if (level < 0) {
            throw HttpException.badRequest("Level cannot be less than zero");
        }
        if (level > MAX_LEVEL) {
            throw HttpException.badRequest("Level cannot be greater than " + MAX_LEVEL);
        }
        return (byte) level;
    }

    public byte getValue() {
        return level;
    }

}