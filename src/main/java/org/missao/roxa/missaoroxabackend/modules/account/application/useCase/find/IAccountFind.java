package org.missao.roxa.missaoroxabackend.modules.account.application.useCase.find;

import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.springframework.data.domain.Page;

public interface IAccountFind {

    Page<AccountResponseDto> all(int page, int pageSize, String sortDirection);

    AccountResponseDto byEmail(String email);

    AccountResponseDto byPhoneNumber(String phoneNumber);

}