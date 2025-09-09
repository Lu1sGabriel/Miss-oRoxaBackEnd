package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeBirthDate;

import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeBirthDateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;

import java.util.UUID;

public interface IUserChangeBirthDate {
    UserResponseDto change(UUID id, UserChangeBirthDateDto dto);
}