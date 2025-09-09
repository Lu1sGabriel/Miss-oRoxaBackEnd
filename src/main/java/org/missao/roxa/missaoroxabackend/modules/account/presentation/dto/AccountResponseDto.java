package org.missao.roxa.missaoroxabackend.modules.account.presentation.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.time.LocalDate;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        String email,
        String phoneNumber,
        byte level,
        byte xp,
        int coin,
        User user
) implements IMappableDto {
    public record User(UUID userId, String fullName, LocalDate birthDate) {
    }
}