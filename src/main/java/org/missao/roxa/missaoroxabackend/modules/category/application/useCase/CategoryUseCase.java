package org.missao.roxa.missaoroxabackend.modules.category.application.useCase;

import lombok.Getter;
import org.missao.roxa.missaoroxabackend.modules.category.application.useCase.update.ICategoryUpdate;
import org.missao.roxa.missaoroxabackend.modules.category.application.useCase.create.ICategoryCreate;
import org.missao.roxa.missaoroxabackend.modules.category.application.useCase.deactivate.ICategoryDeactivate;
import org.missao.roxa.missaoroxabackend.modules.category.application.useCase.find.ICategoryFind;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class CategoryUseCase {
    private final ICategoryFind find;
    private final ICategoryCreate create;
    private final ICategoryUpdate changeName;
    private final ICategoryDeactivate deactivate;

    public CategoryUseCase(ICategoryFind find, ICategoryCreate create, ICategoryUpdate changeName, ICategoryDeactivate deactivate) {
        this.find = find;
        this.create = create;
        this.changeName = changeName;
        this.deactivate = deactivate;
    }

}