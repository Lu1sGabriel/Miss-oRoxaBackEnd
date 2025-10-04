package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeStreet;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeStreetDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.shared.mapper.AddressMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressChangeStreet implements IAddressChangeStreet {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AddressResponseDto change(UUID userId, AddressChangeStreetDto dto) {
        return userRepository.findById(Validator.requireNonEmpty(userId))
                .map(Validator::requireEntityActivated)
                .map(UserEntity::getAddress)
                .map(Validator::requireEntityActivated)
                .map(address -> {
                    address.changeStreet(dto.street());
                    address.getDateInfo().update();
                    addressRepository.save(address);
                    return mapper.toDto(address);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

}