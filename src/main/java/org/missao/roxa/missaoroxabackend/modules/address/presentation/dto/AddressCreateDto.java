package org.missao.roxa.missaoroxabackend.modules.address.presentation.dto;

import java.util.Optional;
import java.util.UUID;

public record AddressCreateDto(
        UUID municipalityId,
        String postalCode,
        String street,
        Optional<String> complement,
        String neighborhood
) {
}