package org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.factory;

import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;

public final class ProductDtoFactory {

    private ProductDtoFactory() {
    }

    public static ProductResponseDto toDto(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ProductResponseDto(
                entity.getId(),
                entity.getName().getValue(),
                entity.getDescription().getValue(),
                entity.getCommerceInfo().getPrice().getValue(),
                entity.getCommerceInfo().getAvailable().getValue()
        );
    }

}