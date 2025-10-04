package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeComplement;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeComplementDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.shared.mapper.AddressMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressChangeComplement implements IAddressChangeComplement {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    @Override
    public AddressResponseDto change(UUID userId, AddressChangeComplementDto dto) {
        return userRepository.findById(Validator.requireNonEmpty(userId))
                .map(Validator::requireEntityActivated)
                .map(UserEntity::getAddress)
                .map(Validator::requireEntityActivated)
                .map(address -> {
                    address.changeComplement(dto.complement());
                    address.getDateInfo().update();
                    addressRepository.save(address);
                    return mapper.toDto(address);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

}