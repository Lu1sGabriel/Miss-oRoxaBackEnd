package org.missao.roxa.missaoroxabackend.core.shared.helper.uuid;

import com.github.f4b6a3.uuid.UuidCreator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serial;
import java.io.Serializable;

public final class UuidV7Generator implements IdentifierGenerator, Serializable {

    @Serial
    private static final long serialVersionUID = -6926492576635743740L;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return UuidCreator.getTimeOrderedEpoch();
    }

}