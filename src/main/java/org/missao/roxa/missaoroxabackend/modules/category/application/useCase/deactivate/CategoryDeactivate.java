package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.deactivate;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.category.infrastructure.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryDeactivate implements ICategoryDeactivate {
    private final CategoryRepository categoryRepository;


    @Override
    public void deactivate(UUID id) {
        var category = categoryRepository.findById(Validator.requireNonEmpty(id))
                .map(Validator::requireEntityActivated)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with the provided ID."));

        checkSubCategoriesActive(category);

        category.getProducts().forEach(product -> {
            if (product.getDateInfo().getDeletedAt() == null) {
                throw new DataConflictException(
                        "This category has dependent products. To deactivate it, you must first either move the products to a new category or deactivate them."
                );
            }
        });

        category.getDateInfo().deactivate();
        categoryRepository.save(category);
    }

    private void checkSubCategoriesActive(CategoryEntity category) {
        category.getSubCategories().forEach(sub -> {
            if (sub.getDateInfo().getDeletedAt() == null) {
                throw new DataConflictException(
                        "This category has other dependent categories. To deactivate it, you must first deactivate its dependents."
                );
            }
            checkSubCategoriesActive(sub);
        });
    }

}