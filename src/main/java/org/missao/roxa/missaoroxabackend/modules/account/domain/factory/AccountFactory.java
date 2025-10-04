package org.missao.roxa.missaoroxabackend.modules.account.domain.factory;

import org.missao.roxa.missaoroxabackend.modules.account.domain.entity.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.metadata.AccountCredentials;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Password;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;

public final class AccountFactory {

    private AccountFactory() {
    }

    public static AccountEntity create(UserEntity user, AccountCreateDto dto) {
        var account = new AccountEntity(
                user,
                new AccountCredentials(
                        new Email(dto.email()),
                        new Password(dto.password()),
                        new PhoneNumber(dto.phoneNumber())
                )
        );
        user.setAccount(account);
        return account;
    }

}