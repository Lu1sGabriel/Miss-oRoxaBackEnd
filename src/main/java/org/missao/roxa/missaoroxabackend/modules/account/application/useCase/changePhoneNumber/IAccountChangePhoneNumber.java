package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePhoneNumber;

import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePhoneNumberDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;

import java.util.UUID;

public interface IAccountChangePhoneNumber {
    AccountResponseDto change(UUID userId, AccountChangePhoneNumberDto dto);
}