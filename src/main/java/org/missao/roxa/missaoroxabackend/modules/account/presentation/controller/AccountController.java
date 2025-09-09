package org.missao.roxa.missaoroxabackend.modules.account.presentation.controller;

import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.AccountUseCase;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangeEmailDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePhoneNumberDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {
    private final AccountUseCase useCase;

    public AccountController(AccountUseCase useCase) {
        this.useCase = useCase;
    }

    @PatchMapping(value = "/change-email/{user-id}")
    public ResponseEntity<AccountResponseDto> changeEmail(@PathVariable(value = "user-id") UUID userId,
                                                          @RequestBody AccountChangeEmailDto dto) {
        var account = useCase.changeEmail().change(userId, dto);
        return ResponseEntity.ok().body(account);
    }

    @PatchMapping(value = "/change-phone-number/{user-id}")
    public ResponseEntity<AccountResponseDto> changePhoneNumber(@PathVariable(value = "user-id") UUID userId,
                                                                @RequestBody AccountChangePhoneNumberDto dto) {
        var account = useCase.changePhoneNumber().change(userId, dto);
        return ResponseEntity.ok().body(account);
    }

}