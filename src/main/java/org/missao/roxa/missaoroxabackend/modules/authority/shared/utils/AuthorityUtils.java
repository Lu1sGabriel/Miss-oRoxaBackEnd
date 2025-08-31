package org.missao.roxa.missaoroxabackend.modules.authority.shared.utils;

import org.missao.roxa.missaoroxabackend.modules.authority.domain.enums.IAuthority;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AuthorityUtils {

    private static final Map<String, IAuthority> PERMISSION_MAP = new HashMap<>();

    static {
        Reflections reflections = new Reflections("org.missao.roxa.missaoroxabackend.modules.authority.domain.enums");

        Set<Class<? extends IAuthority>> authorityEnums = reflections.getSubTypesOf(IAuthority.class);

        for (Class<? extends IAuthority> aClass : authorityEnums) {
            if (aClass.isEnum()) {
                IAuthority[] enumConstants = aClass.getEnumConstants();
                for (IAuthority authority : enumConstants) {
                    PERMISSION_MAP.put(authority.getName(), authority);
                }
            }
        }
    }

    private AuthorityUtils() {
    }

    public static IAuthority[] getAllPermissions() {
        return PERMISSION_MAP.values().toArray(new IAuthority[0]);
    }

    public static IAuthority fromValue(String value) {
        IAuthority permission = PERMISSION_MAP.get(value);
        if (permission == null) {
            throw new IllegalArgumentException("Unknown permission value: " + value);
        }
        return permission;
    }

}