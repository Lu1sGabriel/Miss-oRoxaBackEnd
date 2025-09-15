package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.find;

import jakarta.persistence.EntityNotFoundException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PageableUtils;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.address.domain.value.PostalCode;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddressFind implements IAddressFind {
    private final AddressRepository addressRepository;
    private final MunicipalityRepository municipalityRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    public AddressFind(AddressRepository addressRepository, MunicipalityRepository municipalityRepository,
                       StateRepository stateRepository, UserRepository userRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.municipalityRepository = municipalityRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDto byUser(UUID userId) {
        return userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(userId))
                .map(UserEntity::getAddress)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressResponseDto> byPostalCode(String postalCode, int page, int size, String sort) {
        Pageable pageable = PageableUtils.createPageable(page, size, "id", sort);
        return mapper.toDtoPage(addressRepository.findByPostalCode(new PostalCode(
                PredicatesValidator.requireSearchParamNotNullAndBlank(postalCode)
        ), pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressResponseDto> byMunicipality(UUID municipalityId, int page, int size, String sort) {
        return municipalityRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(municipalityId))
                .map(municipality -> {
                    Pageable pageable = PageableUtils.createPageable(page, size, "postalCode", sort);
                    var addresses = addressRepository.findByMunicipality(municipality, pageable);
                    return mapper.toDtoPage(addresses);
                })
                .orElseThrow(() -> new EntityNotFoundException("Municipality not found with the provided ID"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressResponseDto> byState(String stateName, int page, int size, String sort) {
        return stateRepository.findByName_NameIgnoreCase(PredicatesValidator.requireSearchParamNotNullAndBlank(stateName))
                .map(state -> {
                    Pageable pageable = PageableUtils.createPageable(page, size, "municipality.name", sort);
                    var addresses = addressRepository.findByMunicipality_State(state, pageable);
                    return mapper.toDtoPage(addresses);
                })
                .orElseThrow(() -> new EntityNotFoundException("State not found with the name."));
    }

}