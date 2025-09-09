package org.missao.roxa.missaoroxabackend.modules.states.application.useCase;

import org.missao.roxa.missaoroxabackend.modules.states.application.useCase.find.IStateFind;
import org.springframework.stereotype.Component;

@Component
public record StateUseCase(
        IStateFind find
) {
}
