package org.missao.roxa.missaoroxabackend.modules.address.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper implements IMapper<AddressResponseDto, AddressEntity> {
    @Override
    public AddressResponseDto toDto(AddressEntity addressEntity) {
        return AddressFactoryDto.fromEntity(addressEntity);
    }

    @Override
    public List<AddressResponseDto> toDtoList(List<AddressEntity> addressEntities) {
        return addressEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<AddressResponseDto> toDtoPage(Page<AddressEntity> addressEntities) {
        return addressEntities.map(this::toDto);
    }

}