package org.missao.roxa.missaoroxabackend.modules.states.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.entity.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.states.domain.value.State;
import org.missao.roxa.missaoroxabackend.modules.states.domain.value.UF;
import org.missao.roxa.missaoroxabackend.modules.states.domain.value.UFCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "states")
@Getter
@NoArgsConstructor
public class StateEntity implements Serializable, IMappableEntity {

    @Serial
    private static final long serialVersionUID = -1720815635074787384L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @AttributeOverride(name = "fullName", column = @Column(name = "name", nullable = false, unique = true))
    private State name;

    @AttributeOverride(name = "code", column = @Column(name = "uf_code", nullable = false, unique = true))
    private UFCode ufCode;

    @AttributeOverride(name = "uf", column = @Column(name = "uf", nullable = false, unique = true))
    private UF uf;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<MunicipalityEntity> municipalities = new HashSet<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof StateEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}