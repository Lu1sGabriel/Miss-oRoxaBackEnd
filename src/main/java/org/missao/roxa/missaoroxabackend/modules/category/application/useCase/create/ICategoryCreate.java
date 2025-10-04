package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.create;

import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryCreateDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;

public interface ICategoryCreate {
    CategoryResponseDto create(CategoryCreateDto dto);
}