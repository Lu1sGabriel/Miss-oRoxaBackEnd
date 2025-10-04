package org.missao.roxa.missaoroxabackend.modules.account.presentation.dto;

import org.missao.roxa.missaoroxabackend.modules.account.domain.entity.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;

public final class AccountFactoryDto {

    private AccountFactoryDto() {
    }

    public static AccountResponseDto fromEntity(AccountEntity entity) {
        if (entity == null) return null;

        return new AccountResponseDto(
                entity.getId(),
                entity.getCredentials().getEmail().getValue(),
                entity.getCredentials().getPhoneNumber().getValue(),
                entity.getGamification().getLevel().getValue(),
                entity.getGamification().getXp().getValue(),
                entity.getGamification().getCoin().getValue(),
                fromUser(entity.getUser())
        );
    }

    private static AccountResponseDto.User fromUser(UserEntity entity) {
        if (entity == null) return null;

        return new AccountResponseDto.User(
                entity.getId(),
                entity.getFullName().getValue(),
                entity.getBirthDate().getValue()
        );
    }

}