package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePhoneNumber;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.account.domain.entity.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePhoneNumberDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.account.shared.mapper.AccountMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountChangePhoneNumber implements IAccountChangePhoneNumber {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountResponseDto change(UUID userId, AccountChangePhoneNumberDto dto) {
        return userRepository.findById(Validator.requireNonEmpty(userId))
                .map(Validator::requireEntityActivated)
                .map(UserEntity::getAccount)
                .map(Validator::requireEntityActivated)
                .map(accountEntity -> isPhoneNumberUnique(accountEntity, dto.phoneNumber()))
                .map(account -> {
                    account.getCredentials().changePhoneNumber(dto.phoneNumber());
                    account.getDateInfo().update();
                    accountRepository.save(account);
                    return mapper.toDto(account);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    private AccountEntity isPhoneNumberUnique(AccountEntity accountEntity, String phoneNumber) {
        if (accountRepository.findByCredentials_PhoneNumber(new PhoneNumber(phoneNumber)).isPresent()) {
            throw new DataConflictException("There is already an account associated with this mobile number.");
        }
        return accountEntity;
    }

}