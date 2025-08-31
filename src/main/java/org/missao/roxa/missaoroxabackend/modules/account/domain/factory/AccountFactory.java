package org.missao.roxa.missaoroxabackend.modules.account.domain.factory;

import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.metadata.AccountCredentials;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Password;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.dto.AccountCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;

public final class AccountFactory {

    private AccountFactory() {
    }

    public static AccountEntity create(UserEntity user, AccountCreateDto dto) {
        return new AccountEntity(
                user,
                new AccountCredentials(
                        new Email(dto.email()),
                        new Password(dto.password()),
                        new PhoneNumber(dto.phoneNumber())
                )
        );
    }

}