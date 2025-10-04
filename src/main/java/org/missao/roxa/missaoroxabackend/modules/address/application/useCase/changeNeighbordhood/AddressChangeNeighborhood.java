package org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeNeighbordhood;

import jakarta.persistence.EntityNotFoundException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressChangeNeighborhoodDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.shared.mapper.AddressMapper;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressChangeNeighborhood implements IAddressChangeNeighborhood {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper mapper;

    public AddressChangeNeighborhood(AddressRepository addressRepository, UserRepository userRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressResponseDto change(UUID userId, AddressChangeNeighborhoodDto dto) {
        return userRepository.findById(Validator.requireNonEmpty(userId))
                .filter(Validator.requireEntityActivated().asPredicate())
                .map(UserEntity::getAddress)
                .filter(Validator.requireEntityActivated().asPredicate())
                .map(address -> {
                    address.changeNeighborhood(dto.neighborhood());
                    address.getDateInfo().update();
                    addressRepository.save(address);
                    return mapper.toDto(address);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

}