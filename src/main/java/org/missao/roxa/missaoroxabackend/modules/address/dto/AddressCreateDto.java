package org.missao.roxa.missaoroxabackend.modules.address.dto;

import java.util.Optional;

public record AddressCreateDto(
        String postalCode,
        String street,
        Optional<String> complement,
        String neighborhood,
        String city,
        String state
) {
}