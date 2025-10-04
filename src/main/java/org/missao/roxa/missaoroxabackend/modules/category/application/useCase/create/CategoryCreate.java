package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.create;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.category.domain.factory.CategoryFactory;
import org.missao.roxa.missaoroxabackend.modules.category.domain.values.CategoryName;
import org.missao.roxa.missaoroxabackend.modules.category.infrastructure.CategoryRepository;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryCreateDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;
import org.missao.roxa.missaoroxabackend.modules.category.shared.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCreate implements ICategoryCreate {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryResponseDto create(CategoryCreateDto dto) {
        validateUniqueConstraint(dto);

        var category = categoryRepository.save(CategoryFactory.create(dto));

        if (dto.parentId() != null) {
            var parent = categoryRepository.findById(dto.parentId())
                    .map(this::validateIfHasProductAssociated)
                    .orElseThrow(() -> new EntityNotFoundException("Category parent not found with the given ID."));
            parent.addSubCategory(category);
        }
        return mapper.toDto(category);

    }

    private void validateUniqueConstraint(CategoryCreateDto dto) {
        if (categoryRepository.findByName(new CategoryName(dto.name()).getValue()).isPresent()) {
            throw new DataConflictException("Already have a category with that name.");
        }
    }

    private CategoryEntity validateIfHasProductAssociated(CategoryEntity categoryEntity) {
        if (categoryEntity.getProducts() != null && !categoryEntity.getProducts().isEmpty()) {
            throw new InvalidRequestDataException("You cannot associate this category as a parent. Because it has associated products.");
        }
        return categoryEntity;
    }

}