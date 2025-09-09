package org.missao.roxa.missaoroxabackend.modules.account.application.useCase;

import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changeEmail.IAccountChangeEmail;
import org.missao.roxa.missaoroxabackend.modules.account.application.useCase.changePhoneNumber.IAccountChangePhoneNumber;
import org.springframework.stereotype.Component;

@Component
public record AccountUseCase(
        IAccountChangeEmail changeEmail,
        IAccountChangePhoneNumber changePhoneNumber
) {
}
