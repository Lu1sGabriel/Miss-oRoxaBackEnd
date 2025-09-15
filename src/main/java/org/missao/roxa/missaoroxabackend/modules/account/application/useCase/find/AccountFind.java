package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.find;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PageableUtils;
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
public class AccountFind implements IAccountFind {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    public AccountFind(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponseDto> all(int page, int pageSize, String sortDirection) {
        Pageable pageable = PageableUtils.createPageable(page, pageSize, "id", sortDirection);
        return mapper.toDtoPage(accountRepository.findAllByDateInfo_DeletedAtIsNull(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto byEmail(final String email) {
        return accountRepository.findByCredentials_Email(new Email(
                        PredicatesValidator.requireSearchParamNotNullAndBlank(email)
                ))
                .filter(PredicatesValidator.isEntityActivated())
                .map(mapper::toDto)
                .orElseThrow(() -> HttpException.notFound("Account not found with the provided email."));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto byPhoneNumber(String phoneNumber) {
        return accountRepository.findByCredentials_PhoneNumber(new PhoneNumber(phoneNumber))
                .filter(PredicatesValidator.isEntityActivated())
                .map(mapper::toDto)
                .orElseThrow(() -> HttpException.notFound("Account not found with the provided phone number."));
    }

}