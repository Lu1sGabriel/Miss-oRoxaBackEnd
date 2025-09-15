package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changeEmail;

import jakarta.persistence.EntityNotFoundException;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangeEmailDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.account.shared.mapper.AccountMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public AccountResponseDto change(final UUID userId, final AccountChangeEmailDto dto) {
        return userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(userId))
                .filter(PredicatesValidator.isEntityActivated())
                .map(UserEntity::getAccount)
                .filter(PredicatesValidator.isEntityActivated())
                .filter(isEmailUnique(dto))
                .map(account -> {
                    account.getCredentials().changeEmail(dto.email());
                    account.getDateInfo().update();
                    accountRepository.save(account);
                    return mapper.toDto(account);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    private Predicate<AccountEntity> isEmailUnique(final AccountChangeEmailDto dto) {
        return account -> {
            if (accountRepository.findByCredentials_Email(new Email(dto.email())).isPresent()) {
                throw new DataConflictException("There is already an account with that email.");
            }
            return true;
        };
    }

}