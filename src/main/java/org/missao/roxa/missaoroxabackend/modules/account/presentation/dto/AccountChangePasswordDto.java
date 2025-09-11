package org.missao.roxa.missaoroxabackend.modules.account.presentation.dto;

public record AccountChangePasswordDto(
        String currentPassword,
        String newPassword,
        String confirmPassword
) {
}