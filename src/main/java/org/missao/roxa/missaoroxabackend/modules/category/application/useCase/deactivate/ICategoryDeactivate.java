package org.missao.roxa.missaoroxabackend.modules.category.application.useCase.deactivate;

import java.util.UUID;

public interface ICategoryDeactivate {
    void deactivate(UUID id);
}