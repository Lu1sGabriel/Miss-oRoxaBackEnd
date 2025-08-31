package org.missao.roxa.missaoroxabackend.modules.authority.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.missao.roxa.missaoroxabackend.modules.authority.domain.enums.IAuthority;
import org.missao.roxa.missaoroxabackend.modules.authority.domain.value.AuthorityDescription;
import org.missao.roxa.missaoroxabackend.modules.authority.domain.value.AuthorityName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@Getter
public class AuthorityEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 235567809685984419L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    @NaturalId
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, unique = true))
    private AuthorityName name;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "description", nullable = false))
    private AuthorityDescription description;

    public AuthorityEntity(IAuthority authority) {
        this.name = new AuthorityName(authority.getName());
        this.description = new AuthorityDescription(authority.getDescription());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AuthorityEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}