package org.missao.roxa.missaoroxabackend.modules.account.domain.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Coin;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Level;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Xp;

@Embeddable
@Getter
public class AccountGamification {

    @Column(name = "level", nullable = false)
    private Level level;

    @Column(name = "xp", nullable = false)
    private Xp xp;

    @Column(name = "coin", nullable = false)
    private Coin coin;

    public AccountGamification() {
        this.level = new Level((byte) 1);
        this.xp = new Xp((byte) 0);
        this.coin = new Coin((byte) 0);
    }

    public AccountGamification(Level level, Xp xp, Coin coin) {
        this.level = level;
        this.xp = xp;
        this.coin = coin;
    }

    public void changeLevel(Level level) {
        this.level = level;
    }

    public void changeXp(Xp xp) {
        this.xp = xp;
    }

    public void changeCoin(Coin coin) {
        this.coin = coin;
    }

}