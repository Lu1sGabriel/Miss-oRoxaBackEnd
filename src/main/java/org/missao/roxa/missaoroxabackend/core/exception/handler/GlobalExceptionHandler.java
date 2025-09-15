package org.missao.roxa.missaoroxabackend.core.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.missao.roxa.missaoroxabackend.core.exception.types.EntityDisabledException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.core.shared.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@ControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidRequestDataException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRequestDataException(InvalidRequestDataException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EntityDisabledException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityDisabledException(EntityDisabledException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorResponseDto> handleNullableException(PropertyValueException ex, HttpServletRequest request) {
        String property = ex.getPropertyName();
        String entity = ex.getEntityName();

        var error = buildErrorResponse(
                String.format("The required field '%s' in entity '%s' was not provided.", property, entity),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidJson(HttpServletRequest request) {
        var error = buildErrorResponse("The JSON sent in the request is invalid. Please check the syntax.", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(HttpServletRequest request) {
        var error = buildErrorResponse("Invalid ID: The given value, is an invalid ID", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(NoResourceFoundException ex, HttpServletRequest request) {
        String formatedMessage = ex.getMessage().replaceFirst("No static resource", "No static resource was found for")
                .replace(".", "");
        var error = buildErrorResponse(formatedMessage, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(HttpServletRequest request) {
        var error = buildErrorResponse("Internal server error. That's not your fault!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ErrorResponseDto buildErrorResponse(String message, String path) {
        return new ErrorResponseDto(message, path, Instant.now());
    }

}