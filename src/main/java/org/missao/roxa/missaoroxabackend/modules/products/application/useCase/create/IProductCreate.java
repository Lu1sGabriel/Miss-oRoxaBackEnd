package org.missao.roxa.missaoroxabackend.modules.products.application.useCase.create;

import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductCreateDto;
import org.missao.roxa.missaoroxabackend.modules.products.presentation.dto.model.ProductResponseDto;

public interface IProductCreate {
    ProductResponseDto create(ProductCreateDto dto);
}