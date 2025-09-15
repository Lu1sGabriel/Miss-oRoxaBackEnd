package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeBirthDate;

import jakarta.transaction.Transactional;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PredicatesValidator;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeBirthDateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserChangeBirthDate implements IUserChangeBirthDate {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserChangeBirthDate(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponseDto change(final UUID id, final UserChangeBirthDateDto dto) {
        return userRepository.findById(PredicatesValidator.requireSearchParamNotNullAndBlank(id))
                .filter(PredicatesValidator.isEntityActivated())
                .map(user -> {
                    user.changeBirthDate(dto.birthDate());
                    userRepository.save(user);
                    return mapper.toDto(user);
                })
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));
    }

}