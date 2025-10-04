package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeBirthDate;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserChangeBirthDateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserChangeBirthDate implements IUserChangeBirthDate {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponseDto change(final UUID id, final UserChangeBirthDateDto dto) {
        return userRepository.findById(Validator.requireNonEmpty(id))
                .map(Validator::requireEntityActivated)
                .map(user -> {
                    user.changeBirthDate(dto.birthDate());
                    user.getDateInfo().update();
                    userRepository.save(user);
                    return mapper.toDto(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with the provided ID."));
    }

}