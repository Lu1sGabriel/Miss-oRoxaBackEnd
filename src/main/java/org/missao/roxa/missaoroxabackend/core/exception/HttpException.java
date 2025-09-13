package org.missao.roxa.missaoroxabackend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class HttpException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 8064543674038481849L;

    private final int statusCode;

    private HttpException(String message, HttpStatus status) {
        super(message);
        this.statusCode = status.value();
    }

    public static HttpException notFound(String message) {
        return new HttpException(message, HttpStatus.NOT_FOUND);
    }

    public static HttpException badRequest(String message) {
        return new HttpException(message, HttpStatus.BAD_REQUEST);
    }

    public static HttpException conflict(String message) {
        return new HttpException(message, HttpStatus.CONFLICT);
    }

    public static HttpException unauthorized(String message) {
        return new HttpException(message, HttpStatus.UNAUTHORIZED);
    }

    public static HttpException forbidden(String message) {
        return new HttpException(message, HttpStatus.FORBIDDEN);
    }

}