package org.missao.roxa.missaoroxabackend.modules.user.application.useCase;

import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeBirthDate.IUserChangeBirthDate;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeFullName.IUserChangeFullName;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.create.IUserCreate;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.find.IUserFind;
import org.springframework.stereotype.Component;

@Component
public record UserUseCase(
        IUserFind find,
        IUserCreate getCreate,
        IUserChangeFullName changeFullName,
        IUserChangeBirthDate changeBirthDate
) {
}