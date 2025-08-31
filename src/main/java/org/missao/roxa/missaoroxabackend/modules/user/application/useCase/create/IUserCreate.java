package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.create;

import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;

public interface IUserCreate {
    UserResponseDto create(UserCreateDto dto);
}