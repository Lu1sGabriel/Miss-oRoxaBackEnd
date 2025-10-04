package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.create;

import jakarta.persistence.EntityNotFoundException;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.modules.account.domain.factory.AccountFactory;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.address.domain.factory.AddressFactory;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.entity.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.municipality.infrastructure.repository.MunicipalityRepository;
import org.missao.roxa.missaoroxabackend.modules.user.domain.factory.UserFactory;
import org.missao.roxa.missaoroxabackend.modules.user.domain.value.FullName;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserCreate implements IUserCreate {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final MunicipalityRepository municipalityRepository;
    private final UserMapper mapper;

    public UserCreate(UserRepository userRepository, AccountRepository accountRepository, AddressRepository addressRepository,
                      MunicipalityRepository municipalityRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
        this.municipalityRepository = municipalityRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto create(UserCreateDto dto) {
        validateUniqueConstraint(dto);

        final var user = userRepository.save(UserFactory.create(dto));
        accountRepository.save(AccountFactory.create(user, dto.account()));

        final var municipality = findMunicipality(dto.address().municipalityId());
        addressRepository.save(AddressFactory.create(user, municipality, dto.address()));

        return mapper.toDto(user);
    }

    private void validateUniqueConstraint(UserCreateDto dto) {
        if (userRepository.findByName(new FullName(dto.fullName()).getValue()).isPresent()) {
            throw new DataConflictException("A user with that name already exists.");
        }
        if (accountRepository.findByCredentials_Email(new Email(dto.account().email())).isPresent()) {
            throw new DataConflictException("A user with that email already exists.");
        }
        if (accountRepository.findByCredentials_PhoneNumber(new PhoneNumber(dto.account().phoneNumber())).isPresent()) {
            throw new DataConflictException("A user with that phone number already exists.");
        }
    }

    private MunicipalityEntity findMunicipality(UUID id) {
        return municipalityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Municipality not found with the provide ID"));
    }

}