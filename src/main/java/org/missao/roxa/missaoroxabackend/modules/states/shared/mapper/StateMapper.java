package org.missao.roxa.missaoroxabackend.modules.states.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.states.domain.entity.StateEntity;
import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StateMapper implements IMapper<StateResponseDto, StateEntity> {
    @Override
    public StateResponseDto toDto(StateEntity entity) {
        return StateFactoryDto.fromEntity(entity);
    }

    @Override
    public StateResponseDto toDtoWithRelatedEntities(StateEntity entity) {
        return StateFactoryDto.fromEntityWithRelatedEntities(entity);
    }

    @Override
    public List<StateResponseDto> toDtoList(List<StateEntity> stateEntities) {
        return stateEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<StateResponseDto> toDtoPage(Page<StateEntity> stateEntities) {
        return stateEntities.map(this::toDto);
    }

}