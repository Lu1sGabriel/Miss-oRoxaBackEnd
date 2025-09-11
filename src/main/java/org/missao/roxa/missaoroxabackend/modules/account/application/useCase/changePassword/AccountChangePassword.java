package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePassword;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePasswordDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Predicate;

@Service
public class AccountChangePassword implements IAccountChangePassword {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountChangePassword(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void change(UUID userId, AccountChangePasswordDto dto) {
        if (userId == null) {
            throw HttpException.badRequest("User ID must not be null.");
        }

        userRepository.findById(userId)
                .filter(validateUserIsActive())
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

    private Predicate<UserEntity> validateUserIsActive() {
        return user -> {
            if (user.getDateInfo().getDeletedAt() != null) {
                throw HttpException.badRequest(
                        "This user is deactivated. Please, contact us if you want to activate it again."
                );
            }
            return true;
        };
    }

}
