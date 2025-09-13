package org.missao.roxa.missaoroxabackend.modules.account.application.useCase;

import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changeEmail.IAccountChangeEmail;
import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePassword.IAccountChangePassword;
import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePhoneNumber.IAccountChangePhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.find.IAccountFind;
import org.springframework.stereotype.Component;

@Component
public record AccountUseCase(
        IAccountFind find,
        IAccountChangeEmail changeEmail,
        IAccountChangePhoneNumber changePhoneNumber,
        IAccountChangePassword changePassword
) {
}
