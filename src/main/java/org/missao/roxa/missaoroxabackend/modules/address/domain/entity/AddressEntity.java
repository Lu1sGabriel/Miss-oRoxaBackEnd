package org.missao.roxa.missaoroxabackend.modules.address.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.common.entity.domain.IEntity;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.core.shared.helper.uuid.UUIDV7;
import org.missao.roxa.missaoroxabackend.modules.address.domain.metaData.AddressDateInfo;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.Complement;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.Neighborhood;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.PostalCode;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.Street;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.entity.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
public class AddressEntity implements Serializable, IMappableEntity, IEntity {

    @Serial
    private static final long serialVersionUID = -1054928725160826558L;

    @Id
    @UUIDV7
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "municipality_id", nullable = false)
    private MunicipalityEntity municipality;

    @Embedded
    @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code", nullable = false))
    private PostalCode postalCode;

    @Embedded
    @AttributeOverride(name = "street", column = @Column(name = "street", nullable = false))
    private Street street;

    @Embedded
    @AttributeOverride(name = "complement", column = @Column(name = "complement"))
    private Complement complement;

    @Embedded
    @AttributeOverride(name = "neighborhood", column = @Column(name = "neighborhood", nullable = false))
    private Neighborhood neighborhood;

    @Embedded
    private AddressDateInfo dateInfo;

    public AddressEntity(UserEntity user, MunicipalityEntity municipality, PostalCode postalCode,
                         Street street, Complement complement, Neighborhood neighborhood) {
        this.user = user;
        this.municipality = municipality;
        this.postalCode = postalCode;
        this.street = street;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.dateInfo = new AddressDateInfo();
    }

    public void changePostalCode(String postalCode) {
        this.postalCode = new PostalCode(postalCode);
    }

    public void changeStreet(String street) {
        this.street = new Street(street);
    }

    public void changeComplement(String complement) {
        if (complement == null) {
            this.complement = null;
        }
        this.complement = new Complement(complement);
    }

    public void changeNeighborhood(String neighborhood) {
        this.neighborhood = new Neighborhood(neighborhood);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AddressEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}