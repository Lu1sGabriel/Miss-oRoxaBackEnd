package org.missao.roxa.missaoroxabackend.core.exception.types;

public class InvalidRequestParameterException extends RuntimeException {
    public InvalidRequestParameterException(String message) {
        super(message);
    }
}
