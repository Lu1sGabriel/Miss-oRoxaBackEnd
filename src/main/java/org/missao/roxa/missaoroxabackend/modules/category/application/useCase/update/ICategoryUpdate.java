package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.update;

import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryUpdateDto;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryResponseDto;

import java.util.UUID;

public interface ICategoryUpdate {
    CategoryResponseDto change(UUID id, CategoryUpdateDto dto);
}