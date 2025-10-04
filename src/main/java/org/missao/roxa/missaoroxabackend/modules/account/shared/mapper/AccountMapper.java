package org.missao.roxa.missaoroxabackend.modules.account.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.account.domain.entity.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.account.presentation.dto.AccountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper implements IMapper<AccountResponseDto, AccountEntity> {

    @Override
    public AccountResponseDto toDto(AccountEntity entity) {
        return AccountFactoryDto.fromEntity(entity);
    }

    @Override
    public List<AccountResponseDto> toDtoList(List<AccountEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<AccountResponseDto> toDtoPage(Page<AccountEntity> entities) {
        return entities.map(this::toDto);
    }

}