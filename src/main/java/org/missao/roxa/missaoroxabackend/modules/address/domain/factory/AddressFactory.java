package org.missao.roxa.missaoroxabackend.modules.address.domain.factory;

import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.*;
import org.missao.roxa.missaoroxabackend.modules.address.dto.AddressCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;

public final class AddressFactory {

    private AddressFactory() {
    }

    public static AddressEntity create(UserEntity user, AddressCreateDto dto) {
        return new AddressEntity(
                user,
                new PostalCode(dto.postalCode()),
                new Street(dto.street()),
                new Complement(dto.complement().orElse(null)),
                new Neighborhood(dto.neighborhood()),
                new City(dto.city()),
                new State(dto.state())
        );
    }

}