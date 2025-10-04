package org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDto(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        boolean availability
) implements IMappableDto {
}