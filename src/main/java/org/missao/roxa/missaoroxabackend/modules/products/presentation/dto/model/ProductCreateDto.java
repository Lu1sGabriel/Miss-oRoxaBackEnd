package org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateDto(
        String name,
        String description,
        UUID category,
        BigDecimal price,
        boolean isAvailable
) {
}