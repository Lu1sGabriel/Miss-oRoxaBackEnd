package org.missao.roxa.missaoroxabackend.modules.address.presentation.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.time.LocalDate;
import java.util.UUID;

public record AddressResponseDto(UUID id, User user, Municipality municipality, String postalCode, String street,
                                 String complement, String neighborhood) implements IMappableDto {

    public AddressResponseDto(UUID id, UUID userId, String userName, LocalDate birthDate, UUID municipalityId, String municipalityName,
                              UUID stateId, String stateName, String stateUfCode, String stateUf, String postalCode, String street, String complement, String neighborhood) {

        this(id, new User(userId, userName, birthDate), new Municipality(municipalityId, municipalityName, new State(stateId, stateName, stateUfCode, stateUf)),
                postalCode, street, complement, neighborhood);
    }

    public AddressResponseDto(UUID id, UUID userId, UUID municipalityId, String municipalityName,
                              UUID stateId, String stateName, String stateUfCode, String stateUf, String postalCode, String street, String complement, String neighborhood) {

        this(id, new User(userId, null, null), new Municipality(municipalityId, municipalityName, new State(stateId, stateName, stateUfCode, stateUf)),
                postalCode, street, complement, neighborhood);
    }

    private record User(UUID id, String name, LocalDate birthDate) {
    }

    private record Municipality(UUID id, String municipality, State state) {
    }

    private record State(UUID id, String state, String ufCode, String uf) {
    }

}