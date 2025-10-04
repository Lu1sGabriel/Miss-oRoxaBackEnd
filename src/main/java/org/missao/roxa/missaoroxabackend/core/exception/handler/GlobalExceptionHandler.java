package org.missao.roxa.missaoroxabackend.core.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.missao.roxa.missaoroxabackend.core.exception.types.DataConflictException;
import org.missao.roxa.missaoroxabackend.core.exception.types.EntityDisabledException;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;
import org.missao.roxa.missaoroxabackend.core.shared.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = InvalidRequestDataException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRequestDataException(InvalidRequestDataException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = EntityDisabledException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityDisabledException(EntityDisabledException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(value = DataConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityDisabledException(DataConflictException ex, HttpServletRequest request) {
        var error = buildErrorResponse(ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = PropertyValueException.class)
    public ResponseEntity<ErrorResponseDto> handleNullableException(PropertyValueException ex, HttpServletRequest request) {
        String property = ex.getPropertyName();
        String entity = ex.getEntityName();

        var error = buildErrorResponse(
                String.format("O campo obrigatório '%s' da entidade '%s' não foi informado.", property, entity),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidJson(HttpServletRequest request) {
        var error = buildErrorResponse(
                "O JSON enviado na requisição é inválido. Verifique a sintaxe e tente novamente.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(HttpServletRequest request) {
        var error = buildErrorResponse(
                "ID inválido fornecido. Verifique se o valor é correto e tente novamente.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        String formattedMessage = ex.getMessage().replaceFirst("No static resource", "Nenhum recurso estático encontrado para: ");
        var error = buildErrorResponse(formattedMessage, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String message = String.format(
                "HTTP method '%s' is not supported for this endpoint. Supported methods: %s",
                ex.getMethod(),
                ex.getSupportedHttpMethods() != null ? ex.getSupportedHttpMethods() : "none"
        );

        var error = buildErrorResponse(message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(HttpServletRequest request) {
        var error = buildErrorResponse(
                "Erro interno no servidor. Ocorreu um problema inesperado, mas não é culpa sua.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private static ErrorResponseDto buildErrorResponse(String error, String path) {
        return new ErrorResponseDto(error, path, Instant.now());
    }

}