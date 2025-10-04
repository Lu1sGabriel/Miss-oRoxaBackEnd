package org.missao.roxa.missaoroxabackend.modules.products.application.useCase.update;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.shared.utils.Validator;
import org.missao.roxa.missaoroxabackend.modules.category.infrastructure.CategoryRepository;
import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductName;
import org.missao.roxa.missaoroxabackend.modules.products.infrastructure.repository.ProductRepository;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductUpdateDto;
import org.missao.roxa.missaoroxabackend.modules.products.shared.mapper.ProductMapper;
import org.missao.roxa.missaoroxabackend.modules.products.shared.util.ProductValidator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductUpdate implements IProductUpdate {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDto update(UUID id, ProductUpdateDto dto) {
        return productRepository.findById(Validator.requireNonEmpty(id))
                .map(Validator::requireEntityActivated)
                .map(product -> {
                    applyChanges(product, dto);
                    product.getDateInfo().update();
                    productRepository.save(product);
                    return mapper.toDto(product);
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the provided ID"));
    }

    private void applyChanges(ProductEntity productEntity, ProductUpdateDto dto) {
        dto.getNameOpt().ifPresent(name -> {
            validateUniqueName(name);
            productEntity.changeName(name);
        });

        dto.getDescriptionOpt().ifPresent(productEntity::changeDescription);
        dto.getPriceOpt().ifPresent(productEntity.getCommerceInfo()::changePrice);
        dto.getAvailabilityOpt().ifPresent(productEntity.getCommerceInfo()::changeAvailability);

        dto.getCategoryIdOpt().ifPresent(categoryId -> {
            var category = categoryRepository.findById(categoryId)
                    .map(ProductValidator::checkIfCategoryIsNotParent)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with the provided ID."));
            productEntity.changeCategory(category);
        });
    }

    private void validateUniqueName(String name) {
        if (productRepository.findByName(new ProductName(name).getValue()).isPresent()) {
            throw new DataConflictException("Already have a product with that name.");
        }
    }

}