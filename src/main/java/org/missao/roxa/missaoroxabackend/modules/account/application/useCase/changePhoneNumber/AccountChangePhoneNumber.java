package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePhoneNumber;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
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
        if (userId == null) {
            throw HttpException.badRequest("User ID cannot be null.");
        }

        return userRepository.findById(userId)
                .filter(validateUserIsActive())
                .map(UserEntity::getAccount)
                .filter(validePhoneNumberUniqueConstraint(dto.phoneNumber()))
                .map(account -> {
                    account.getCredentials().changePhoneNumber(dto.phoneNumber());
                    accountRepository.save(account);
                    return mapper.toDto(account);
                })
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));
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

    private Predicate<AccountEntity> validePhoneNumberUniqueConstraint(String phoneNumber) {
        return AccountEntity -> {
            if (accountRepository.findByCredentials_PhoneNumber(new PhoneNumber(phoneNumber)).isPresent()) {
                throw HttpException.conflict(
                        "There is already an account associated with this mobile number."
                );
            }
            return true;
        };
    }

}