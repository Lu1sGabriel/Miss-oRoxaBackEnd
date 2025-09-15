package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeFullName;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.FullName;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeFullNameDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserChangeFullName implements IUserChangeFullName {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserChangeFullName(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponseDto change(final UUID id, final UserChangeFullNameDto dto) {
        if (id == null) {
            throw new InvalidRequestDataException("The provided ID cannot be null.");
        }

        return userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(id))
                .filter(PredicatesValidator.isEntityActivated())
                .filter(validateUniqueFullName(dto))
                .map(user -> {
                    user.changeFullName(dto.fullName());
                    userRepository.save(user);
                    return mapper.toDto(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

    private Predicate<UserEntity> validateUniqueFullName(final UserChangeFullNameDto dto) {
        return user -> {
            if (userRepository.findByFullName(new FullName(dto.fullName()).getValue()).isPresent()) {
                throw new DataConflictException("An user with that full name already exists.");
            }
            return true;
        };
    }

}