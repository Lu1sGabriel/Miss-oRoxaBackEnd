package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.update;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.category.domain.values.CategoryName;
import org.missao.roxa.missaoroxabackend.modules.category.infrastructure.CategoryRepository;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryUpdateDto;
import org.missao.roxa.missaoroxabackend.modules.category.shared.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryUpdate implements ICategoryUpdate {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryResponseDto change(UUID id, CategoryUpdateDto dto) {
        return categoryRepository.findById(Validator.requireNonEmpty(id))
                .map(Validator::requireEntityActivated)
                .map(category -> {
                    applyUpdates(category, dto);
                    category.getDateInfo().update();
                    categoryRepository.save(category);
                    return mapper.toDto(category);
                })
                .orElseThrow(() -> new EntityNotFoundException("Category not found with the provided ID."));
    }

    private void applyUpdates(CategoryEntity category, CategoryUpdateDto dto) {
        dto.getNameOpt().ifPresent(name -> {
            validateIfNameIsUnique(name);
            category.changeName(name);
        });

        dto.getDescriptionOpt().ifPresent(category::changeDescription);

        dto.getParentIdOpt().ifPresent(parentId -> {
            var parent = categoryRepository.findById(parentId)
                    .map(this::validateIfHasProductAssociated)
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found with the provided ID."));
            category.changeParent(parent);
        });
    }

    private void validateIfNameIsUnique(String name) {
        categoryRepository.findByName(new CategoryName(name).getValue())
                .ifPresent(existing -> {
                    throw new DataConflictException("Category with the provided name already exists.");
                });
    }

    private CategoryEntity validateIfHasProductAssociated(CategoryEntity categoryEntity) {
        if (categoryEntity.getProducts() != null && !categoryEntity.getProducts().isEmpty()) {
            throw new InvalidRequestDataException("You cannot associate this category as a parent. Because it has associated products.");
        }
        return categoryEntity;
    }

}