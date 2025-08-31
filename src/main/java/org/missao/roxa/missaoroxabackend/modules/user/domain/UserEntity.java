package org.missao.roxa.missaoroxabackend.modules.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.metadata.UserDateInfo;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.BirthDate;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.FullName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserEntity implements Serializable, IMappableEntity {

    @Serial
    private static final long serialVersionUID = 4475953970920573774L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    @AttributeOverride(name = "fullName", column = @Column(name = "full_name", nullable = false, unique = true))
    private FullName fullName;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date", nullable = false))
    private BirthDate birthDate;

    @Embedded
    private UserDateInfo dateInfo;

    public UserEntity(FullName fullName, BirthDate birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.dateInfo = new UserDateInfo();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}