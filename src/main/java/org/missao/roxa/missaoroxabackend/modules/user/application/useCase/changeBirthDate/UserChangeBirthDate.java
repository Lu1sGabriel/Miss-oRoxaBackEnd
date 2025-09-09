package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeBirthDate;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeBirthDateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserChangeBirthDate implements IUserChangeBirthDate {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserChangeBirthDate(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto change(final UUID id, final UserChangeBirthDateDto dto) {
        if (id == null) {
            throw HttpException.badRequest("The provided ID cannot be null.");
        }

        return userRepository.findById(id)
                .filter(validateUserIsActive())
                .map(user -> {
                    user.changeBirthDate(dto.birthDate());
                    userRepository.save(user);
                    return mapper.toDto(user);
                })
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));
    }

    private Predicate<UserEntity> validateUserIsActive() {
        return user -> {
            if (user.getDateInfo().getDeletedAt() != null) {
                throw HttpException.badRequest(
                        "This user is deactivated. Please, contact us if you want to activate it again."
                );
            }
            return true;
        };
    }

}