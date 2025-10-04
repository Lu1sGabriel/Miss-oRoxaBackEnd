package org.missao.roxa.missaoroxabackend.modules.user.application.useCase;

import lombok.Getter;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeBirthDate.IUserChangeBirthDate;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.changeFullName.IUserChangeFullName;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.create.IUserCreate;
import org.missao.roxa.missaoroxabackend.modules.user.application.useCase.find.IUserFind;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class UserUseCase {
    private final IUserFind find;
    private final IUserCreate create;
    private final IUserChangeFullName changeFullName;
    private final IUserChangeBirthDate changeBirthDate;

    public UserUseCase(IUserFind find, IUserCreate create,
                       IUserChangeFullName changeFullName, IUserChangeBirthDate changeBirthDate) {
        this.find = find;
        this.create = create;
        this.changeFullName = changeFullName;
        this.changeBirthDate = changeBirthDate;
    }

}