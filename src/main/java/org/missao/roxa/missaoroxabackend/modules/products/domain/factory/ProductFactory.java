package org.missao.roxa.missaoroxabackend.modules.products.domain.factory;

import org.missao.roxa.missaoroxabackend.core.common.entity.value.Description;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;
import org.missao.roxa.missaoroxabackend.modules.products.domain.metadata.ProductCommerceInfo;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductAvailable;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductName;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductPrice;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductCreateDto;

public final class ProductFactory {

    private ProductFactory() {
    }

    public static ProductEntity create(CategoryEntity category, ProductCreateDto dto) {
        return new ProductEntity(
                category,
                new ProductName(dto.name()),
                new Description(dto.description()),
                new ProductCommerceInfo(
                        new ProductPrice(dto.price()),
                        new ProductAvailable(dto.isAvailable())
                )
        );
    }

}