package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.find;

import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;

import java.util.List;

public interface ICategoryFind {
    List<CategoryResponseDto> all();
}
