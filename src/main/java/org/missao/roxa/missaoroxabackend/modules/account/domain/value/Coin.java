package org.missao.roxa.missaoroxabackend.modules.account.domain.value;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "coin")
public final class Coin {

    private static final int MAX_COIN = 1000;

    private int coin;

    public Coin(int coin) {
        this.coin = validate(coin);
    }

    private static int validate(int coin) {
        if (coin < 0) {
            throw new InvalidRequestDataException("Coin cannot be less than zero.");
        }

        if (coin > MAX_COIN) {
            throw new InvalidRequestDataException("Coin cannot be greater than " + MAX_COIN + ".");
        }

        return coin;
    }

    public int getValue() {
        return coin;
    }

}