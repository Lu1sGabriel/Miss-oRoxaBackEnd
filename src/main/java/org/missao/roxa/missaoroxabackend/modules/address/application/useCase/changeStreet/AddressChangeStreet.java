package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeStreet;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeStreetDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.shared.mapper.AddressMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressChangeStreet implements IAddressChangeStreet {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    public AddressChangeStreet(AddressRepository addressRepository, UserRepository userRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AddressResponseDto change(UUID userId, AddressChangeStreetDto dto) {
        return userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(userId))
                .filter(PredicatesValidator.isEntityActivated())
                .map(UserEntity::getAddress)
                .filter(PredicatesValidator.isEntityActivated())
                .map(address -> {
                    address.changeStreet(dto.street());
                    address.getDateInfo().update();
                    addressRepository.save(address);
                    return mapper.toDto(address);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

}