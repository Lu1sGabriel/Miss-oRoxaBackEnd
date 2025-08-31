package org.missao.roxa.missaoroxabackend.modules.account.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.time.Instant;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        UUID userId,
        String email,
        String password,
        String phoneNumber,
        byte level,
        byte xp,
        int coins,
        Instant createdAt
) implements IMappableDto {
}
