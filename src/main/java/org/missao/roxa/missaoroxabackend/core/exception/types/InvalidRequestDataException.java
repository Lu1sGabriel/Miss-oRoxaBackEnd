package org.missao.roxa.missaoroxabackend.core.exception.types;

import java.io.Serial;
import java.io.Serializable;

public class InvalidRequestDataException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -9217872135230426587L;

    public InvalidRequestDataException(String message) {
        super(message);
    }

}