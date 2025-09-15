package org.missao.roxa.missaoroxabackend.core.shared.utils;

import io.micrometer.common.util.StringUtils;
import org.missao.roxa.missaoroxabackend.core.common.entity.IEntity;
import org.missao.roxa.missaoroxabackend.core.common.entity.IEntityDateInfo;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.util.function.Predicate;

public final class PredicatesValidator {

    private PredicatesValidator() {
    }

    public static <T> T requireSearchParamNotNullAndBlank(T param) {
        if (param == null) {
            throw HttpException.badRequest("Search parameter cannot be null.");
        }
        if (param instanceof String str && StringUtils.isBlank(str)) {
            throw HttpException.badRequest("Search parameter cannot be blank.");
        }
        return param;
    }

    public static <Entity extends IEntity<DateInfo>, DateInfo extends IEntityDateInfo> Predicate<Entity> isEntityActivated() {
        return entity -> {
            if (entity.getDateInfo().getDeletedAt() != null) {
                throw HttpException.badRequest("The requested entity is deactivated.");
            }
            return true;
        };
    }

}