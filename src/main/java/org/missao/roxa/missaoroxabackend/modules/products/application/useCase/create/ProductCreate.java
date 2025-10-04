package org.missao.roxa.missaoroxabackend.modules.products.application.useCase.create;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.modules.category.infrastructure.CategoryRepository;
import org.missao.roxa.missaoroxabackend.modules.products.domain.factory.ProductFactory;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductName;
import org.missao.roxa.missaoroxabackend.modules.products.infrastructure.repository.ProductRepository;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductCreateDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;
import org.missao.roxa.missaoroxabackend.modules.products.shared.mapper.ProductMapper;
import org.missao.roxa.missaoroxabackend.modules.products.shared.util.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCreate implements IProductCreate {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDto create(ProductCreateDto dto) {
        checkUniqueName(dto.name());

        var category = categoryRepository.findById(dto.category())
                .map(ProductValidator::checkIfCategoryIsNotParent)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with the provided ID."));

        var product = ProductFactory.create(category, dto);
        productRepository.save(product);

        return mapper.toDto(product);
    }

    private void checkUniqueName(String name) {
        if (productRepository.findByName(new ProductName(name).getValue()).isPresent()) {
            throw new DataConflictException("A product with that name already exists.");
        }
    }

}