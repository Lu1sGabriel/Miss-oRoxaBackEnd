package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeFullName;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.FullName;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeFullNameDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserChangeFullName implements IUserChangeFullName {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponseDto change(final UUID id, final UserChangeFullNameDto dto) {
        if (id == null) {
            throw new InvalidRequestDataException("The provided ID cannot be null.");
        }

        return userRepository.findById(Validator.requireNonEmpty(id))
                .map(Validator::requireEntityActivated)
                .map(userEntity -> validateUniqueFullName(userEntity, dto))
                .map(user -> {
                    user.changeFullName(dto.fullName());
                    user.getDateInfo().update();
                    userRepository.save(user);
                    return mapper.toDto(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    private UserEntity validateUniqueFullName(UserEntity userEntity, UserChangeFullNameDto dto) {
        if (userRepository.findByName(new FullName(dto.fullName()).getValue()).isPresent()) {
            throw new DataConflictException("An user with that full name already exists.");
        }
        return userEntity;
    }

}