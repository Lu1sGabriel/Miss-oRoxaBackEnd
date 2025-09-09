package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changeEmail;

import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangeEmailDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;

import java.util.UUID;

public interface IAccountChangeEmail {
    AccountResponseDto change(UUID userId, AccountChangeEmailDto email);
}
