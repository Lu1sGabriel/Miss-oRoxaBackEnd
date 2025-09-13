package org.missao.roxa.missaoroxabackend.modules.address.presentation.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.time.LocalDate;
import java.util.UUID;

public record AddressResponseDto(UUID id, String postalCode, String street,
                                 String complement, String neighborhood, User user, Municipality municipality) implements IMappableDto {

    protected record User(UUID id, String name, LocalDate birthDate) {
    }

    protected record Municipality(UUID id, String name, State state) {
    }

    protected record State(UUID id, String state, String ufCode, String uf) {
    }

}