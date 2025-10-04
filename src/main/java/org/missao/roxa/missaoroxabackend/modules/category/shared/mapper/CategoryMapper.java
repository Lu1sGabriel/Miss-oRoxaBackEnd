package org.missao.roxa.missaoroxabackend.modules.category.shared.mapper;

import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMapper;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.factory.CategoryFactoryDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper implements IMapper<CategoryResponseDto, CategoryEntity> {

    @Override
    public CategoryResponseDto toDto(CategoryEntity entity) {
        return CategoryFactoryDto.toDto(entity);
    }

    @Override
    public List<CategoryResponseDto> toDtoList(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Page<CategoryResponseDto> toDtoPage(Page<CategoryEntity> categoryEntities) {
        return categoryEntities.map(this::toDto);
    }

    @Override
    public CategoryResponseDto toDtoWithRelatedEntities(CategoryEntity entity) {
        return CategoryFactoryDto.toDtoWithRelatedEntities(entity);
    }

}