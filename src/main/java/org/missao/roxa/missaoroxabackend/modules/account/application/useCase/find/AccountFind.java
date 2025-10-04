package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.find;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PageableUtils;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.account.shared.mapper.AccountMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountFind implements IAccountFind {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDto> all(int page, int pageSize, String sortDirection) {
        Pageable pageable = PageableUtils.createPageable(page, pageSize, "id", sortDirection);
        return mapper.toDtoPage(accountRepository.findAllByDateInfo_DeletedAtIsNull(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto byEmail(final String email) {
        return accountRepository.findByCredentials_Email(new Email(Validator.requireNonEmpty(email)))
                .map(Validator::requireEntityActivated)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with the provided email."));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto byPhoneNumber(String phoneNumber) {
        return accountRepository.findByCredentials_PhoneNumber(new PhoneNumber(Validator.requireNonEmpty(phoneNumber)))
                .map(Validator::requireEntityActivated)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with the provided phone number."));
    }

}