package org.missao.roxa.missaoroxabackend.core.exception.types;

import java.io.Serial;
import java.io.Serializable;

public class EntityDisabledException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 5784897248228103113L;

    public EntityDisabledException(String message) {
        super(message);
    }

}