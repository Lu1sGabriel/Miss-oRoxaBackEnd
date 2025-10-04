package org.missao.roxa.missaoroxabackend.modules.states.presentation.dto;

import org.missao.roxa.missaoroxabackend.modules.municipality.domain.entity.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.states.domain.entity.StateEntity;

import java.text.Collator;
import java.util.*;

public final class StateFactoryDto {

    private static final Collator collator = Collator.getInstance(Locale.of("pt", "BR"));

    private StateFactoryDto() {
    }

    public static StateResponseDto fromEntity(StateEntity entity) {
        if (entity == null) return null;

        return new StateResponseDto(
                entity.getId(),
                entity.getName().getValue(),
                null
        );
    }

    public static StateResponseDto fromEntityWithRelatedEntities(StateEntity entity) {
        if (entity == null) return null;

        return new StateResponseDto(
                entity.getId(),
                entity.getName().getValue(),
                fromMunicipalities(entity.getMunicipalities().stream().toList())
        );
    }

    private static List<StateResponseDto.Municipality> fromMunicipalities(List<MunicipalityEntity> entities) {
        collator.setStrength(Collator.PRIMARY);

        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(m -> m.getName().getValue(), collator))
                .map(municipality -> new StateResponseDto.Municipality(
                        municipality.getId(),
                        municipality.getName().getValue()
                ))
                .toList();
    }

}