package org.missao.roxa.missaoroxabackend.modules.municipality.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.modules.address.domain.entity.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.value.MunicipalityName;
import org.missao.roxa.missaoroxabackend.modules.states.domain.entity.StateEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "municipalities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "state_id"})
        }
)
@NoArgsConstructor
@Getter
public class MunicipalityEntity implements Serializable, IMappableEntity {

    @Serial
    private static final long serialVersionUID = 7344720087649587146L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    private MunicipalityName name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity state;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses = new HashSet<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MunicipalityEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}