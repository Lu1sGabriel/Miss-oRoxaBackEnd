package org.missao.roxa.missaoroxabackend.core.shared.utils;

import io.micrometer.common.util.StringUtils;
import org.missao.roxa.missaoroxabackend.core.common.entity.domain.IEntity;
import org.missao.roxa.missaoroxabackend.core.exception.types.EntityDisabledException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestParameterException;

public final class Validator {

    private Validator() {
    }

    public static <T> T requireNonEmpty(T param) {
        if (param == null) {
            throw new InvalidRequestParameterException("Search parameter cannot be null.");
        }
        if (param instanceof String str && StringUtils.isBlank(str)) {
            throw new InvalidRequestParameterException("Search parameter cannot be blank.");
        }
        return param;
    }

    public static <Entity extends IEntity> Entity requireEntityActivated(Entity entity) {
        if (entity.getDateInfo().getDeletedAt() != null) {
            throw new EntityDisabledException("The requested entity is deactivated. Please contact us to enable it again.");
        }
        return entity;
    }

}