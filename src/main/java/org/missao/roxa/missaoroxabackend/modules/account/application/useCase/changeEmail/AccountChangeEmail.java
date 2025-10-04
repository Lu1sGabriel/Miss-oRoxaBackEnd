package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changeEmail;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.account.domain.entity.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangeEmailDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.account.shared.mapper.AccountMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountChangeEmail implements IAccountChangeEmail {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountResponseDto change(final UUID userId, final AccountChangeEmailDto dto) {
        return userRepository.findById(Validator.requireNonEmpty(userId))
                .map(Validator::requireEntityActivated)
                .map(UserEntity::getAccount)
                .map(Validator::requireEntityActivated)
                .map(accountEntity -> isEmailUnique(accountEntity, dto))
                .map(account -> {
                    account.getCredentials().changeEmail(dto.email());
                    account.getDateInfo().update();
                    accountRepository.save(account);
                    return mapper.toDto(account);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    private AccountEntity isEmailUnique(AccountEntity accountEntity, AccountChangeEmailDto dto) {
        if (accountRepository.findByCredentials_Email(new Email(dto.email())).isPresent()) {
            throw new DataConflictException("There is already an account with that email.");
        }
        return accountEntity;
    }

}