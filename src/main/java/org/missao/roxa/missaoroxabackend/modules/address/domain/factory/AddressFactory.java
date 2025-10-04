package org.missao.roxa.missaoroxabackend.modules.address.domain.factory;

import org.missao.roxa.missaoroxabackend.modules.address.domain.entity.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.Complement;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.Neighborhood;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.PostalCode;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.Street;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressCreateDto;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.entity.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;

public final class AddressFactory {

    private AddressFactory() {
    }

    public static AddressEntity create(UserEntity user, MunicipalityEntity municipality, AddressCreateDto dto) {
        var address = new AddressEntity(
                user,
                municipality,
                new PostalCode(dto.postalCode()),
                new Street(dto.street()),
                new Complement(dto.complement().orElse(null)),
                new Neighborhood(dto.neighborhood())
        );
        user.setAddress(address);
        return address;
    }

}