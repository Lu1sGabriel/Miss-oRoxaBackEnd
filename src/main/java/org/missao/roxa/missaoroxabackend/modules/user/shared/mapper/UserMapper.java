package org.missao.roxa.missaoroxabackend.modules.user.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.address.dto.AddressResponseDto;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.missao.roxa.missaoroxabackend.modules.user.presentation.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements IMapper<UserResponseDto, UserEntity> {

    @Override
    public UserResponseDto toDto(UserEntity entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getFullName().getValue(),
                entity.getBirthDate().getValue(),
                entity.getDateInfo().getCreatedAt(),
                null,
                null
        );
    }

    public UserResponseDto toDto(UserEntity entity, AccountEntity accountEntity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getFullName().getValue(),
                entity.getBirthDate().getValue(),
                entity.getDateInfo().getCreatedAt(),
                createAccountResponseDto(accountEntity),
                null
        );
    }

    public UserResponseDto toDto(UserEntity userEntity, AccountEntity accountEntity, AddressEntity addressEntity) {
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getFullName().getValue(),
                userEntity.getBirthDate().getValue(),
                userEntity.getDateInfo().getCreatedAt(),
                createAccountResponseDto(accountEntity),
                createAddressResponseDto(addressEntity)
        );
    }

    @Override
    public List<UserResponseDto> toDtoList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<UserResponseDto> toDtoPage(Page<UserEntity> userEntities) {
        return userEntities.map(this::toDto);
    }

    private static AccountResponseDto createAccountResponseDto(AccountEntity accountEntity) {
        return new AccountResponseDto(
                accountEntity.getId(),
                accountEntity.getUser().getId(),
                accountEntity.getCredentials().getEmail().getValue(),
                accountEntity.getCredentials().getPassword().getValue(),
                accountEntity.getCredentials().getPhoneNumber().getValue(),
                accountEntity.getGamification().getLevel().getValue(),
                accountEntity.getGamification().getXp().getValue(),
                accountEntity.getGamification().getCoin().getValue(),
                accountEntity.getDateInfo().getCreatedAt()
        );
    }

    private static AddressResponseDto createAddressResponseDto(AddressEntity addressEntity) {
        return new AddressResponseDto(
                addressEntity.getId(),
                addressEntity.getUser().getId(),
                addressEntity.getPostalCode().getValue(),
                addressEntity.getStreet().getValue(),
                addressEntity.getComplement().getValue(),
                addressEntity.getNeighborhood().getValue(),
                addressEntity.getCity().getValue(),
                addressEntity.getState().getValue(),
                addressEntity.getDateInfo().getCreatedAt()
        );
    }

}