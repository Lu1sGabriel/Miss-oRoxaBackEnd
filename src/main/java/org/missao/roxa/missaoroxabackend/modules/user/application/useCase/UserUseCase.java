package org.missao.roxa.missaoroxabackend.modules.user.application.useCase;

import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.create.IUserCreate;
import org.springframework.stereotype.Component;

@Component
public record UserUseCase(
        IUserCreate getCreate
) {

}