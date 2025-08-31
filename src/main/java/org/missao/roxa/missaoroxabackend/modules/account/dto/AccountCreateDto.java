package org.missao.roxa.missaoroxabackend.modules.account.dto;

public record AccountCreateDto(
        String email,
        String password,
        String phoneNumber
) {
}