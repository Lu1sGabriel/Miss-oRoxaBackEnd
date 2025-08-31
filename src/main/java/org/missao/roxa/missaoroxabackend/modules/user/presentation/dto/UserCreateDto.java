package org.missao.roxa.missaoroxabackend.modules.user.presentation.dto;

import org.missao.roxa.missaoroxabackend.modules.account.dto.AccountCreateDto;
import org.missao.roxa.missaoroxabackend.modules.address.dto.AddressCreateDto;

import java.time.LocalDate;

public record UserCreateDto(
        AccountCreateDto account,
        AddressCreateDto address,
        String fullName,
        LocalDate birthDate
) {
}