package org.missao.roxa.missaoroxabackend.core.exception.types;

import java.io.Serial;
import java.io.Serializable;

public class InvalidRequestParameterException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 8934337210981924021L;

    public InvalidRequestParameterException(String message) {
        super(message);
    }

}