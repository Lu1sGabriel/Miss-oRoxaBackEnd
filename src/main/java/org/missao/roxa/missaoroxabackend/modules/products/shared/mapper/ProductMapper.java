package org.missao.roxa.missaoroxabackend.modules.products.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.factory.ProductDtoFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper implements IMapper<ProductResponseDto, ProductEntity> {

    @Override
    public ProductResponseDto toDto(ProductEntity entity) {
        return ProductDtoFactory.toDto(entity);
    }

    @Override
    public List<ProductResponseDto> toDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<ProductResponseDto> toDtoPage(Page<ProductEntity> productEntities) {
        return productEntities.map(this::toDto);
    }

}