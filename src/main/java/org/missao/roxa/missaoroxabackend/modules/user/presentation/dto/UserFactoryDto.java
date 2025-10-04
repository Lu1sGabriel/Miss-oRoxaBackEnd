package org.missao.roxa.missaoroxabackend.modules.user.presentation.dto;

import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;

public final class UserFactoryDto {

    private UserFactoryDto() {
    }

    public static UserResponseDto fromEntity(UserEntity entity) {
        if (entity == null) return null;

        return new UserResponseDto(
                entity.getId(),
                entity.getFullName().getValue(),
                entity.getBirthDate().getValue(),
                AccountFactoryDto.fromEntity(entity.getAccount()),
                AddressFactoryDto.fromEntity(entity.getAddress())
        );
    }

}