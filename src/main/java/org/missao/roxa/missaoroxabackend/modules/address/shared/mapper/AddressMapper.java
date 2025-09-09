package org.missao.roxa.missaoroxabackend.modules.address.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.address.presentation.dto.AddressResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper implements IMapper<AddressResponseDto, AddressEntity> {
    @Override
    public AddressResponseDto toDto(AddressEntity entity) {
        return new AddressResponseDto(
                entity.getId(),
                entity.getUser().getId(),
                entity.getUser().getFullName().getValue(),
                entity.getUser().getBirthDate().getValue(),
                entity.getMunicipality().getId(),
                entity.getMunicipality().getName().getValue(),
                entity.getMunicipality().getState().getId(),
                entity.getMunicipality().getState().getName().getValue(),
                entity.getMunicipality().getState().getUfCode().getValue(),
                entity.getMunicipality().getState().getUf().getValue(),
                entity.getPostalCode().getValue(),
                entity.getStreet().getValue(),
                entity.getComplement().getValue(),
                entity.getNeighborhood().getValue()
        );
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
