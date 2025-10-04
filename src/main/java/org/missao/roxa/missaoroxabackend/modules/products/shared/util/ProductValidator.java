package org.missao.roxa.missaoroxabackend.modules.products.shared.util;

import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductName;
import org.missao.roxa.missaoroxabackend.modules.products.infrastructure.repository.ProductRepository;

public final class ProductValidator {

    private ProductValidator() {
    }

    public static CategoryEntity checkIfCategoryIsNotParent(CategoryEntity categoryEntity) {
        if (categoryEntity.getParent() == null) {
            throw new InvalidRequestDataException("The chosen category cannot be a top-level category.");
        }
        if (categoryEntity.getSubCategories() != null && !categoryEntity.getSubCategories().isEmpty()) {
            throw new InvalidRequestDataException("The chosen category must not have subcategories.");
        }
        return categoryEntity;
    }

    public static void checkIfNameIsUnique(ProductRepository repository, String name) {
        if (repository.findByName(new ProductName(name).getValue()).isPresent()) {
            throw new DataConflictException("Already have a product with that name.");
        }
    }

}