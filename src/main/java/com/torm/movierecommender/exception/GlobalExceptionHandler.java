package com.torm.movierecommender.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto constraintViolationExceptionHandler(ConstraintViolationException e) {
        List<ExceptionResponseDto.ArgumentError> argumentErrors = e.getConstraintViolations()
                .stream()
                .map(constraintViolation -> {
                    String propertyPath = constraintViolation.getPropertyPath().toString();
                    String argument = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);

                    return new ExceptionResponseDto.ArgumentError(
                            argument,
                            constraintViolation.getMessage()
                    );
                })
                .toList();

        return new ExceptionResponseDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "ARGUMENT_INVALID_ERROR",
                argumentErrors
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ExceptionResponseDto.ArgumentError> argumentErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ExceptionResponseDto.ArgumentError(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        ))
                .toList();

        return new ExceptionResponseDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "ARGUMENT_INVALID_ERROR",
                argumentErrors
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponseDto> responseStatusExceptionHandler(ResponseStatusException e) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                Instant.now(),
                e.getStatusCode().value(),
                e.getReason(),
                null
        );

        return new ResponseEntity<>(exceptionResponseDto, e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto runtimeExceptionHandler(Exception e) {
        // log

        return new ExceptionResponseDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                null
        );
    }
}