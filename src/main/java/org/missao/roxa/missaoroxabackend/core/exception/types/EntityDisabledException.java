package org.missao.roxa.missaoroxabackend.core.exception.types;

public class EntityDisabledException extends RuntimeException {
    public EntityDisabledException(String message) {
        super(message);
    }
}