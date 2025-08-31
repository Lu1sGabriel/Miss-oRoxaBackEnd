package org.missao.roxa.missaoroxabackend.modules.user.domain.factory;

import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.BirthDate;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.FullName;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;

public final class UserFactory {

    private UserFactory() {
    }

    public static UserEntity create(UserCreateDto dto) {
        return new UserEntity(
                new FullName(dto.fullName()),
                new BirthDate(dto.birthDate())
        );
    }

}