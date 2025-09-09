package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.find;

import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IAddressFind {
    AddressResponseDto byUser(UUID userId);

    Page<AddressResponseDto> byPostalCode(String postalCode, int page, int size, String sort);

    Page<AddressResponseDto> byMunicipality(UUID municipalityId, int page, int size, String sort);

    Page<AddressResponseDto> byState(String stateName, int page, int size, String sort);
}