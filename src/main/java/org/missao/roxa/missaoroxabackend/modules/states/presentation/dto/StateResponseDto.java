package org.missao.roxa.missaoroxabackend.modules.states.presentation.dto;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.util.List;
import java.util.UUID;

public record StateResponseDto(
        UUID id,
        String name,
        List<Municipality> municipality
) implements IMappableDto {

    protected record Municipality(UUID id, String name) {
    }

}