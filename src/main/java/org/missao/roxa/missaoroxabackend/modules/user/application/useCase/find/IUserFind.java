package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.find;

import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IUserFind {

    Page<UserResponseDto> all(int page, int pageSize, String SortDirection);

    UserResponseDto byId(UUID id);

    UserResponseDto byFullName(String fullName);

    List<UserResponseDto> byBirthdays(Integer month, Integer day);

}