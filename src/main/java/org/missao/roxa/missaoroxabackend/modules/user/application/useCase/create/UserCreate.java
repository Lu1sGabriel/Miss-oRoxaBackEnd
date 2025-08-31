package org.missao.roxa.missaoroxabackend.modules.user.application.useCase.create;

import org.missao.roxa.missaoroxabackend.core.exception.HttpException;
import org.missao.roxa.missaoroxabackend.modules.account.domain.factory.AccountFactory;
import org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository.AccountRepository;
import org.missao.roxa.missaoroxabackend.modules.address.domain.factory.AddressFactory;
import org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository.AddressRepository;
import org.missao.roxa.missaoroxabackend.modules.user.domain.factory.UserFactory;
import org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository.UserRepository;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserCreateDto;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCreate implements IUserCreate {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final UserMapper mapper;

    public UserCreate(UserRepository userRepository, AccountRepository accountRepository,
                      AddressRepository addressRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto create(UserCreateDto dto) {
        validateUniqueConstraint(dto);

        final var user = userRepository.save(UserFactory.create(dto));
        final var account = accountRepository.save(AccountFactory.create(user, dto.account()));
        final var address = addressRepository.save(AddressFactory.create(user, dto.address()));

        return mapper.toDto(user, account, address);
    }


    private void validateUniqueConstraint(UserCreateDto dto) {
        if (userRepository.findByFullName_FullName(dto.fullName()).isPresent()) {
            throw HttpException.conflict("A user with that name already exists.");
        }

        if (accountRepository.findByCredentials_Email_Email(dto.account().email()).isPresent()) {
            throw HttpException.conflict("A user with that email already exists.");
        }

        if (accountRepository.findByCredentials_PhoneNumber_Number(dto.account().phoneNumber()).isPresent()) {
            throw HttpException.conflict("A user with that phone number already exists.");
        }
    }

}