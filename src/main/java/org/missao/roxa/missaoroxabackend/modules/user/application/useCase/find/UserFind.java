package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.find;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.PageableUtils;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.FullName;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserFind implements IUserFind {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserFind(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<UserResponseDto> all(final int page, final int pageSize, final String sortDirection) {
        Pageable pageable = PageableUtils.createPageable(page, pageSize, "id", sortDirection);
        return mapper.toDtoPage(userRepository.findAll(pageable));
    }

    @Override
    public UserResponseDto byId(final UUID id) {
        if (id == null) {
            throw HttpException.badRequest("ID must not be null.");
        }

        return userRepository.findById(id)
                .filter(validateUserIsActive())
                .map(mapper::toDto)
                .orElseThrow(() -> HttpException.notFound("User not found with the provide ID."));
    }

    @Override
    public UserResponseDto byFullName(final String fullName) {
        if (fullName == null) {
            throw HttpException.badRequest("Full name must not be null.");
        }

        return userRepository.findByFullName(new FullName(fullName).getValue())
                .filter(validateUserIsActive())
                .map(mapper::toDto)
                .orElseThrow(() -> HttpException.notFound("User not found with the given ID"));
    }

    @Override
    public List<UserResponseDto> byBirthdays(Integer month, Integer day) {
        if (day == null && month == null) {
            return mapper.toDtoList(userRepository.findBirthdayOfWeek());
        }

        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        validateMonth(month);

        if (day != null) {
            validateDayOfMonth(day, month);
            return mapper.toDtoList(userRepository.findBirthdayOfDay(day, month));
        }

        return mapper.toDtoList(userRepository.findBirthdayOfMonth(month));
    }

    private void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw HttpException.badRequest("Month must be between 1 and 12.");
        }
    }

    private void validateDayOfMonth(int day, int month) {
        int maxDay = YearMonth.of(LocalDate.now().getYear(), month).lengthOfMonth();
        if (day < 1 || day > maxDay) {
            throw HttpException.badRequest(
                    String.format("Invalid day %d for month %d. Max allowed is %d.", day, month, maxDay)
            );
        }
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