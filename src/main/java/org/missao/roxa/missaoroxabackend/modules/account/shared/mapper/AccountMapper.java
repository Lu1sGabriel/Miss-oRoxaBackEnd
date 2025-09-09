package org.missao.roxa.missaoroxabackend.modules.account.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper implements IMapper<AccountResponseDto, AccountEntity> {

    @Override
    public AccountResponseDto toDto(AccountEntity entity) {
        return new AccountResponseDto(
                entity.getId(),
                entity.getCredentials().getEmail().getValue(),
                entity.getCredentials().getPhoneNumber().getValue(),
                entity.getGamification().getLevel().getValue(),
                entity.getGamification().getXp().getValue(),
                entity.getGamification().getCoin().getValue(),
                new AccountResponseDto.User(
                        entity.getUser().getId(),
                        entity.getUser().getFullName().getValue(),
                        entity.getUser().getBirthDate().getValue()
                ));
    }

    @Override
    public List<AccountResponseDto> toDtoList(List<AccountEntity> accountEntities) {
        return accountEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<AccountResponseDto> toDtoPage(Page<AccountEntity> accountEntities) {
        return accountEntities.map(this::toDto);
    }

}