package org.missao.roxa.missaoroxabackend.modules.address.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.time.Instant;
import java.util.UUID;

public record AddressResponseDto(
        UUID id,
        UUID userId,
        String postalCode,
        String street,
        String complement,
        String neighborhood,
        String city,
        String state,
        Instant createdAt
) implements IMappableDto {
}
