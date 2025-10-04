package org.missao.roxa.missaoroxabackend.modules.products.application.useCase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.modules.products.application.useCase.create.IProductCreate;
import org.missao.roxa.missaoroxabackend.modules.products.application.useCase.update.IProductUpdate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public final class ProductUseCase {
    private final IProductCreate create;
    private final IProductUpdate update;
}