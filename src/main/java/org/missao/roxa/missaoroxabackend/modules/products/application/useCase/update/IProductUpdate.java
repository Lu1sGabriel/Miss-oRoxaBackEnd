package org.missao.roxa.missaoroxabackend.modules.products.application.useCase.update;

import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductUpdateDto;

import java.util.UUID;

public interface IProductUpdate {
    ProductResponseDto update(UUID id, ProductUpdateDto dto);
}