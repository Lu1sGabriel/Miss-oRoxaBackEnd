package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.find;

import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.modules.category.infrastructure.CategoryRepository;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;
import org.missao.roxa.missaoroxabackend.modules.category.shared.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFind implements ICategoryFind {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryResponseDto> all() {
        return mapper.toDtoList(categoryRepository.findAllWhereParentIsNull());
    }

}