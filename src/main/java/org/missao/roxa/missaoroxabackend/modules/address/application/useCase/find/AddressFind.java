package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.find;

import io.micrometer.common.util.StringUtils;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PageableUtils;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.shared.mapper.AddressMapper;
import org.missao.roxa.missaoroxabackend.modules.municipality.infrastructure.repository.MunicipalityRepository;
import org.missao.roxa.missaoroxabackend.modules.states.infrastructure.repository.StateRepository;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressFind implements IAddressFind {
    private final AddressRepository addressRepository;
    private final MunicipalityRepository municipalityRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    public AddressFind(AddressRepository addressRepository, MunicipalityRepository municipalityRepository, StateRepository stateRepository,
                       UserRepository userRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.municipalityRepository = municipalityRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressResponseDto byUser(UUID userId) {
        if (userId == null) {
            throw HttpException.badRequest("User id cannot be null.");
        }

        return userRepository.findById(userId)
                .map(UserEntity::getAddress)
                .map(mapper::toDto)
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));
    }

    @Override
    public Page<AddressResponseDto> byPostalCode(String postalCode, int page, int size, String sort) {
        if (postalCode == null || StringUtils.isBlank(postalCode)) {
            throw HttpException.badRequest("Postal code cannot be null or blank.");
        }
        Pageable pageable = PageableUtils.createPageable(page, size, "id", sort);
        return mapper.toDtoPage(addressRepository.findByPostalCode_PostalCode(postalCode.trim(), pageable));
    }

    @Override
    public Page<AddressResponseDto> byMunicipality(UUID municipalityId, int page, int size, String sort) {
        if (municipalityId == null) {
            throw HttpException.badRequest("Municipality ID cannot be null or blank.");
        }

        return municipalityRepository.findById(municipalityId)
                .map(municipality -> {
                    Pageable pageable = PageableUtils.createPageable(page, size, "postalCode", sort);
                    var addresses = addressRepository.findByMunicipality(municipality, pageable);
                    return mapper.toDtoPage(addresses);
                })
                .orElseThrow(() -> HttpException.notFound("Municipality not found with the provided ID"));
    }

    @Override
    public Page<AddressResponseDto> byState(String stateName, int page, int size, String sort) {
        if (stateName == null || StringUtils.isBlank(stateName)) {
            throw HttpException.badRequest("State fullName cannot be null or blank.");
        }

        return stateRepository.findByName_NameIgnoreCase(stateName.trim())
                .map(state -> {
                    Pageable pageable = PageableUtils.createPageable(page, size, "municipality.name", sort);
                    var addresses = addressRepository.findByMunicipality_State(state, pageable);
                    return mapper.toDtoPage(addresses);
                })
                .orElseThrow(() -> HttpException.notFound("State not found with the name."));
    }

}