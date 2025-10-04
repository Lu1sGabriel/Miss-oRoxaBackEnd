package org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CategoryResponseDto(
        UUID id,
        String name,
        String description,
        UUID parentId,
        List<CategoryChildren> children,
        List<Product> products
) implements IMappableDto {

    public record CategoryChildren(
            UUID id,
            String name,
            String description,
            List<CategoryChildren> children
    ) {
    }

    public record Product(
            UUID id,
            String name,
            String description,
            BigDecimal price,
            boolean isAvailable
    ) {
    }
}
