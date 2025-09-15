package org.missao.roxa.missaoroxabackend.modules.address.presentation.dto;

import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.states.domain.StateEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;

public final class AddressFactoryDto {

    private AddressFactoryDto() {
    }

    public static AddressResponseDto fromEntity(AddressEntity entity) {
        if (entity == null) return null;

        return new AddressResponseDto(
                entity.getId(),
                entity.getPostalCode().getValue(),
                entity.getStreet().getValue(),
                (entity.getComplement() != null) ? entity.getComplement().getValue() : null,
                entity.getNeighborhood().getValue(),
                fromUser(entity.getUser()),
                fromMunicipality(entity.getMunicipality())
        );
    }

    private static AddressResponseDto.User fromUser(UserEntity entity) {
        if (entity == null) return null;

        return new AddressResponseDto.User(
                entity.getId(),
                entity.getFullName().getValue(),
                entity.getBirthDate().getValue()
        );
    }

    private static AddressResponseDto.Municipality fromMunicipality(MunicipalityEntity entity) {
        if (entity == null) return null;

        return new AddressResponseDto.Municipality(
                entity.getId(),
                entity.getName().getValue(),
                fromState(entity.getState())
        );
    }

    private static AddressResponseDto.State fromState(StateEntity entity) {
        if (entity == null) return null;

        return new AddressResponseDto.State(
                entity.getId(),
                entity.getName().getValue(),
                entity.getUfCode().getValue(),
                entity.getUf().getValue()
        );
    }

}