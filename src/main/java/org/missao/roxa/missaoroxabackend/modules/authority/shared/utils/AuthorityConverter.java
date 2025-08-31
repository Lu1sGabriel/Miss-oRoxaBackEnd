package org.missao.roxa.missaoroxabackend.modules.authority.shared.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.missao.roxa.missaoroxabackend.modules.authority.domain.enums.IAuthority;

@Converter(autoApply = true)
public class AuthorityConverter implements AttributeConverter<IAuthority, String> {

    @Override
    public String convertToDatabaseColumn(IAuthority attribute) {
        return attribute != null ? attribute.getName() : null;
    }

    @Override
    public IAuthority convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        return AuthorityUtils.fromValue(dbData);
    }

}