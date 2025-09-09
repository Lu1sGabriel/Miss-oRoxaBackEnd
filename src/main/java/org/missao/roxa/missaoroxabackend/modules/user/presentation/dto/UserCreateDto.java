package org.missao.roxa.missaoroxabackend.modules.user.presentation.dto;

import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountCreateDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressCreateDto;

import java.time.LocalDate;

public record UserCreateDto(
        String fullName,
        LocalDate birthDate,
        AccountCreateDto account,
        AddressCreateDto address
) {
}