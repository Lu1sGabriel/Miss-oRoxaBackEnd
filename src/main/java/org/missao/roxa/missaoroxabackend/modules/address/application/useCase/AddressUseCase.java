package org.missao.roxa.missaoroxabackend.modules.address.application.useCase;

import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.find.IAddressFind;
import org.springframework.stereotype.Component;

@Component
public record AddressUseCase(
        IAddressFind find
) {
}
