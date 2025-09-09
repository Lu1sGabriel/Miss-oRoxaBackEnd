package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.find;

import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserFind {

    UserResponseDto byId(UUID id);

    Page<UserResponseDto> allByOrderById(String order, Pageable pageable);

    UserResponseDto byFullName(final String fullName);

    List<UserResponseDto> getBirthdays(Integer month, Integer day);

}