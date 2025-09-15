package org.missao.roxa.missaoroxabackend.core.exception.types;

import java.io.Serial;
import java.io.Serializable;

public class DataConflictException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 7112028815690550008L;

    public DataConflictException(String message) {
        super(message);
    }

}