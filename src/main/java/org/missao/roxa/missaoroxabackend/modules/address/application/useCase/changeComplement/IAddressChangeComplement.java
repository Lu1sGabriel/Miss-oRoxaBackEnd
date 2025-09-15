package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeComplement;

import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeComplementDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;

import java.util.UUID;

public interface IAddressChangeComplement {
    AddressResponseDto change(UUID userId, AddressChangeComplementDto dto);
}
