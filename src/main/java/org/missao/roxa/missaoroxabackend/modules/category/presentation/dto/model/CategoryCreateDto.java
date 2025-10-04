package org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model;

import java.util.UUID;

public record CategoryCreateDto(
        String name,
        String description,
        UUID parentId
) {
}