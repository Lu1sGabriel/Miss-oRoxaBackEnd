package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeNeighbordhood;

import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeNeighborhoodDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;

import java.util.UUID;

public interface IAddressChangeNeighborhood {
    AddressResponseDto change(UUID userId, AddressChangeNeighborhoodDto dto);
}