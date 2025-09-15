package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "xp")
public final class Xp {

    private static final byte MAX_XP = 100;

    private byte xp;

    public Xp(byte xp) {
        this.xp = validate(xp);
    }

    private static byte validate(byte xp) {
        if (xp < 0) {
            throw new InvalidRequestDataException("Xp cannot be less than zero.");
        }

        if (xp % 5 != 0) {
            throw new InvalidRequestDataException("Xp must be a divisor of five.");
        }

        if (xp > MAX_XP) {
            throw new InvalidRequestDataException("Xp cannot be greater than " + MAX_XP + ".");
        }

        return xp;
    }


    public byte getValue() {
        return xp;
    }

}