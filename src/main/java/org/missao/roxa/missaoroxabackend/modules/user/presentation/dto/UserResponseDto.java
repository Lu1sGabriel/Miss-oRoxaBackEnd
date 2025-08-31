package org.missao.roxa.missaoroxabackend.modules.user.presentation.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;
import org.missao.roxa.missaoroxabackend.modules.account.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.dto.AddressResponseDto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String fullName,
        LocalDate birthDate,
        Instant createdAt,
        AccountResponseDto account,
        AddressResponseDto address
) implements IMappableDto {
}