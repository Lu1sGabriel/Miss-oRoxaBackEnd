package org.missao.roxa.missaoroxabackend.modules.account.presentation.dto;

public record AccountCreateDto(
        String email,
        String password,
        String phoneNumber
) {
}