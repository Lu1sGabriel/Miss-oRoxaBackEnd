package org.missao.roxa.missaoroxabackend.modules.user.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
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
                createAccountResponseDto(entity.getAccount()),
                createAddressResponseDto(entity.getAddress())
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
                accountEntity.getCredentials().getEmail().getValue(),
                accountEntity.getCredentials().getPhoneNumber().getValue(),
                accountEntity.getGamification().getLevel().getValue(),
                accountEntity.getGamification().getXp().getValue(),
                accountEntity.getGamification().getCoin().getValue(),
                new AccountResponseDto.User(
                        accountEntity.getUser().getId(),
                        accountEntity.getUser().getFullName().getValue(),
                        accountEntity.getUser().getBirthDate().getValue()
                )
        );
    }

    private static AddressResponseDto createAddressResponseDto(AddressEntity addressEntity) {
        return new AddressResponseDto(
                addressEntity.getId(),
                addressEntity.getUser().getId(),
                addressEntity.getMunicipality().getId(),
                addressEntity.getMunicipality().getName().getValue(),
                addressEntity.getMunicipality().getState().getId(),
                addressEntity.getMunicipality().getState().getName().getValue(),
                addressEntity.getMunicipality().getState().getUfCode().getValue(),
                addressEntity.getMunicipality().getState().getUf().getValue(),
                addressEntity.getPostalCode().getValue(),
                addressEntity.getStreet().getValue(),
                addressEntity.getComplement().getValue(),
                addressEntity.getNeighborhood().getValue()
        );
    }

}