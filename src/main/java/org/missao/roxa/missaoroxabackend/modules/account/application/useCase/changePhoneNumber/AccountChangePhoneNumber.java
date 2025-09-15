package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePhoneNumber;

import jakarta.persistence.EntityNotFoundException;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePhoneNumberDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.account.shared.mapper.AccountMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Predicate;

@Service
public class AccountChangePhoneNumber implements IAccountChangePhoneNumber {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    public AccountChangePhoneNumber(AccountRepository accountRepository, UserRepository userRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountResponseDto change(UUID userId, AccountChangePhoneNumberDto dto) {
        return userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(userId))
                .filter(PredicatesValidator.isEntityActivated())
                .map(UserEntity::getAccount)
                .filter(validePhoneNumberUniqueConstraint(dto.phoneNumber()))
                .map(account -> {
                    account.getCredentials().changePhoneNumber(dto.phoneNumber());
                    accountRepository.save(account);
                    return mapper.toDto(account);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    private Predicate<AccountEntity> validePhoneNumberUniqueConstraint(String phoneNumber) {
        return AccountEntity -> {
            if (accountRepository.findByCredentials_PhoneNumber(new PhoneNumber(phoneNumber)).isPresent()) {
                throw new DataConflictException("There is already an account associated with this mobile number.");
            }
            return true;
        };
    }

}