package org.missao.roxa.missaoroxabackend.modules.user.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements IMapper<UserResponseDto, UserEntity> {

    @Override
    public UserResponseDto toDto(UserEntity entity) {
        return UserFactoryDto.fromEntity(entity);
    }

    @Override
    public List<UserResponseDto> toDtoList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<UserResponseDto> toDtoPage(Page<UserEntity> userEntities) {
        return userEntities.map(this::toDto);
    }

}