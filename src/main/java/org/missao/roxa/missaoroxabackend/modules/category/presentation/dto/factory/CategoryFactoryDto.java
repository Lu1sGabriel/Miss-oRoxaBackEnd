package org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.factory;

import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;
import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;

import java.util.Collections;
import java.util.List;

public final class CategoryFactoryDto {

    private CategoryFactoryDto() {
    }

    public static CategoryResponseDto toDto(CategoryEntity entity) {
        return new CategoryResponseDto(
                entity.getId(),
                entity.getName().getValue(),
                entity.getDescription().getValue(),
                (entity.getParent() != null) ? entity.getParent().getId() : null,
                createChildren(entity),
                null
        );
    }

    public static CategoryResponseDto toDtoWithRelatedEntities(CategoryEntity entity) {
        return new CategoryResponseDto(
                entity.getId(),
                entity.getName().getValue(),
                entity.getDescription().getValue(),
                (entity.getParent() != null) ? entity.getParent().getId() : null,
                createChildren(entity),
                createProduct(entity.getProducts().stream().toList())
        );
    }

    public static List<CategoryResponseDto.CategoryChildren> createChildren(CategoryEntity parent) {
        if (parent == null) {
            return null;
        }

        if (parent.getSubCategories() == null || parent.getSubCategories().isEmpty()) {
            return Collections.emptyList();
        }

        return parent.getSubCategories()
                .stream()
                .filter(subCategory -> subCategory.getDateInfo().getDeletedAt() == null)
                .map(subCategory -> new CategoryResponseDto.CategoryChildren(
                        subCategory.getId(),
                        subCategory.getName().getValue(),
                        subCategory.getDescription().getValue(),
                        createChildren(subCategory)
                ))
                .toList();
    }

    public static List<CategoryResponseDto.Product> createProduct(List<ProductEntity> products) {
        if (products == null || products.isEmpty()) {
            return null;
        }

        return products.stream()
                .filter(product -> product.getDateInfo().getDeletedAt() == null)
                .map(product -> (
                        new CategoryResponseDto.Product(
                                product.getId(),
                                product.getName().getValue(),
                                product.getDescription().getValue(),
                                product.getCommerceInfo().getPrice().getValue(),
                                product.getCommerceInfo().getAvailable().getValue()
                        )
                ))
                .toList();
    }

}