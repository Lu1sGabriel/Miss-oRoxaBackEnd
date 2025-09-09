package org.missao.roxa.missaoroxabackend.modules.states.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.states.domain.StateEntity;
import org.missao.roxa.missaoroxabackend.modules.states.presentation.dto.StateResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Component
public class StateMapper implements IMapper<StateResponseDto, StateEntity> {
    @Override
    public StateResponseDto toDto(StateEntity entity) {
        return new StateResponseDto(entity.getId(), entity.getName().getValue(), null);
    }

    @Override
    public StateResponseDto toDtoWithRelatedEntities(StateEntity entity) {
        Collator collator = Collator.getInstance(Locale.of("pt", "BR"));
        collator.setStrength(Collator.PRIMARY);

        return new StateResponseDto(
                entity.getId(),
                entity.getName().getValue(),
                entity.getMunicipalities().stream()
                        .sorted(Comparator.comparing(m -> m.getName().getValue(), collator))
                        .map(
                                municipality -> new StateResponseDto.Municipality(
                                        municipality.getId(),
                                        municipality.getName().getValue()
                                )
                        ).toList()
        );
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