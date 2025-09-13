package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changeEmail;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangeEmailDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.account.shared.mapper.AccountMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Predicate;

@Service
public class AccountChangeEmail implements IAccountChangeEmail {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    public AccountChangeEmail(AccountRepository accountRepository, UserRepository userRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public AccountResponseDto change(final UUID userId, final AccountChangeEmailDto dto) {
        if (userId == null) {
            throw HttpException.badRequest("User ID cannot be null.");
        }

        return userRepository.findById(userId)
                .filter(validateUserIsActive())
                .map(UserEntity::getAccount)
                .filter(validateUniqueEmailConstraint(dto))
                .map(account -> {
                    account.getCredentials().changeEmail(dto.email());
                    account.getDateInfo().update();
                    accountRepository.save(account);
                    return mapper.toDto(account);
                })
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));
    }

    private Predicate<AccountEntity> validateUniqueEmailConstraint(final AccountChangeEmailDto dto) {
        return account -> {
            if (accountRepository.findByCredentials_Email(new Email(dto.email())).isPresent()) {
                throw HttpException.conflict("There is already an account with that email.");
            }
            return true;
        };
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