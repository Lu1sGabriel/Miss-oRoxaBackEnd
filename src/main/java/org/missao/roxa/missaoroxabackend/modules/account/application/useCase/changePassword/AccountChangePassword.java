package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePassword;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePasswordDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountChangePassword implements IAccountChangePassword {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountChangePassword(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(UUID userId, AccountChangePasswordDto dto) {
        userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(userId))
                .filter(PredicatesValidator.isEntityActivated())
                .map(UserEntity::getAccount)
                .ifPresentOrElse(account -> {
                    account.getCredentials().checkIfPasswordMatch(dto.currentPassword());

                    if (!dto.newPassword().equals(dto.confirmPassword())) {
                        throw HttpException.badRequest("The confirmation password must match the new password.");
                    }

                    account.getCredentials().changePassword(dto.newPassword());
                    accountRepository.save(account);
                }, () -> {
                    throw HttpException.notFound("User not found with the provided id.");
                });
    }

}