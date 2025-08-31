package org.missao.roxa.missaoroxabackend.modules.address.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.modules.address.domain.metaData.AddressDateInfo;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.*;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
public class AddressEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1054928725160826558L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

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
    @AttributeOverride(name = "city", column = @Column(name = "city", nullable = false))
    private City city;

    @Embedded
    @AttributeOverride(name = "state", column = @Column(name = "state", nullable = false))
    private State state;

    @Embedded
    private AddressDateInfo dateInfo;

    public AddressEntity(UserEntity userEntity, PostalCode postalCode, Street street, Complement complement, Neighborhood neighborhood, City city, State state) {
        this.user = userEntity;
        this.postalCode = postalCode;
        this.street = street;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.dateInfo = new AddressDateInfo();
    }

    public void changePostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public void changeStreet(Street street) {
        this.street = street;
    }

    public void changeComplement(Complement complement) {
        this.complement = complement;
    }

    public void changeNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void changeCity(City city) {
        this.city = city;
    }

    public void changeState(State state) {
        this.state = state;
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