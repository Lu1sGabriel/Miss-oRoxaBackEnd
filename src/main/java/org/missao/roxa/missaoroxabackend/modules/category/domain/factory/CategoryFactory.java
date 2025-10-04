package org.missao.roxa.missaoroxabackend.modules.category.domain.factory;

import org.missao.roxa.missaoroxabackend.core.common.entity.value.Description;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.category.domain.values.CategoryName;
import org.missao.roxa.missaoroxabackend.modules.category.presentation.dto.model.CategoryCreateDto;

public final class CategoryFactory {

    public static CategoryEntity create(CategoryCreateDto dto) {
        return new CategoryEntity(
                new CategoryName(dto.name()),
                new Description(dto.description())
        );
    }

}