package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changePostalCode;

import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangePostalCodeDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;

import java.util.UUID;

public interface IAddressChangePostalCode {
    AddressResponseDto change(UUID userId, AddressChangePostalCodeDto dto);
}
