package org.missao.roxa.missaoroxabackend.modules.user.presentation.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String fullName,
        LocalDate birthDate,
        AccountResponseDto account,
        AddressResponseDto address
) implements IMappableDto {
}