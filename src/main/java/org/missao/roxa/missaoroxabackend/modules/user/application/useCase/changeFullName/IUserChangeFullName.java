package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeFullName;

import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeFullNameDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;

import java.util.UUID;

public interface IUserChangeFullName {
    UserResponseDto change(UUID id, UserChangeFullNameDto dto);
}
