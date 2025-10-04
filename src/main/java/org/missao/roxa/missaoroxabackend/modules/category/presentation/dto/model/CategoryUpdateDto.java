package org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model;

import java.util.Optional;
import java.util.UUID;

public record CategoryUpdateDto(
        String name,
        String description,
        UUID parentId
) {

    public Optional<String> getNameOpt() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getDescriptionOpt() {
        return Optional.ofNullable(description);
    }

    public Optional<UUID> getParentIdOpt() {
        return Optional.ofNullable(parentId);
    }

}