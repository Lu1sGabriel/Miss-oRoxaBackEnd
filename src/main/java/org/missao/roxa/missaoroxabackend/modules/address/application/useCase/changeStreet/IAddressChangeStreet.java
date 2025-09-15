package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeStreet;

import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeStreetDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;

import java.util.UUID;

public interface IAddressChangeStreet {
    AddressResponseDto change(UUID userId, AddressChangeStreetDto dto);
}
