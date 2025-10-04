package org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record ProductUpdateDto(
        String name,
        String description,
        BigDecimal price,
        Boolean availability,
        UUID categoryId
) {

    public Optional<String> getNameOpt() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getDescriptionOpt() {
        return Optional.ofNullable(description);
    }

    public Optional<BigDecimal> getPriceOpt() {
        return Optional.ofNullable(price);
    }

    public Optional<Boolean> getAvailabilityOpt() {
        return Optional.ofNullable(availability);
    }

    public Optional<UUID> getCategoryIdOpt() {
        return Optional.ofNullable(categoryId);
    }

}