package org.missao.roxa.missaoroxabackend.modules.account.presentation.controller;

import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.AccountUseCase;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangeEmailDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePasswordDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountChangePhoneNumberDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<Page<AccountResponseDto>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(value = "page-size", required = false, defaultValue = "20") int pageSize,
                                                           @RequestParam(value = "sort-direction", required = false, defaultValue = "ASC") String sortDirection) {
        var account = useCase.find().all(page, pageSize, sortDirection);
        return ResponseEntity.ok().body(account);
    }

    @GetMapping(value = "/by-email/{email}")
    public ResponseEntity<AccountResponseDto> getByEmail(@PathVariable(value = "email") String email) {
        var account = useCase.find().byEmail(email);
        return ResponseEntity.ok().body(account);
    }

    @GetMapping(value = "/by-phone-number/{phone-number}")
    public ResponseEntity<AccountResponseDto> getByPhoneNumber(@PathVariable(value = "phone-number") String phoneNumber) {
        var account = useCase.find().byPhoneNumber(phoneNumber);
        return ResponseEntity.ok().body(account);
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

    @PatchMapping(value = "/change-password/{user-id}")
    public ResponseEntity<String> changePassword(@PathVariable(value = "user-id") UUID userId,
                                                 @RequestBody AccountChangePasswordDto dto) {
        useCase.changePassword().change(userId, dto);
        return ResponseEntity.ok().body("Password changed successfully.");
    }

}