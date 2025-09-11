package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePassword;

import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePasswordDto;

import java.util.UUID;

public interface IAccountChangePassword {
    void change(UUID userId, AccountChangePasswordDto dto);
}