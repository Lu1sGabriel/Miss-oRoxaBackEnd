package org.missao.roxa.missaoroxabackend.modules.account.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.common.entity.domain.IEntity;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.core.shared.helper.uuid.UUIDV7;
import org.missao.roxa.missaoroxabackend.modules.account.domain.metadata.AccountCredentials;
import org.missao.roxa.missaoroxabackend.modules.account.domain.metadata.AccountDateInfo;
import org.missao.roxa.missaoroxabackend.modules.account.domain.metadata.AccountGamification;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
public class AccountEntity implements Serializable, IMappableEntity, IEntity {

    @Serial
    private static final long serialVersionUID = -2773921389709128763L;

    @Id
    @UUIDV7
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Embedded
    private AccountCredentials credentials;

    @Embedded
    private AccountGamification gamification;

    @Embedded
    private AccountDateInfo dateInfo;

    public AccountEntity(UserEntity userEntity, AccountCredentials credentials) {
        this.user = userEntity;
        this.credentials = credentials;
        this.gamification = new AccountGamification();
        this.dateInfo = new AccountDateInfo();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AccountEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}