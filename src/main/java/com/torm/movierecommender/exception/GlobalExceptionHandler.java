package com.torm.movierecommender.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.engine.HibernateConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Object extractPayload(ConstraintViolation<?> constraintViolation) {
        if (constraintViolation == null)
            return Collections.emptyMap();

        HibernateConstraintViolation<?> hibernateConstraintViolation = constraintViolation.unwrap(HibernateConstraintViolation.class);

        Object dynamicPayload = hibernateConstraintViolation.getDynamicPayload(Object.class);

        if (dynamicPayload != null)
            return dynamicPayload;

        return constraintViolation.getConstraintDescriptor()
                .getAttributes()
                .entrySet()
                .stream()
                .filter(entry -> !List.of("message", "groups", "payload").contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

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
                            ErrorCode.fromString(constraintViolation.getMessage()),
                            extractPayload(constraintViolation)
                    );
                })
                .toList();

        return new ExceptionResponseDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.ARGUMENT_INVALID_ERROR,
                argumentErrors
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ExceptionResponseDto.ArgumentError> argumentErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    ConstraintViolation<?> constraintViolation = null;

                    if (fieldError.contains(ConstraintViolation.class))
                        constraintViolation = fieldError.unwrap(ConstraintViolation.class);

                    return new ExceptionResponseDto.ArgumentError(
                            fieldError.getField(),
                            ErrorCode.fromString(fieldError.getDefaultMessage()),
                            extractPayload(constraintViolation));
                })
                .toList();

        return new ExceptionResponseDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.ARGUMENT_INVALID_ERROR,
                argumentErrors
        );
    }

    @ExceptionHandler(ResponseStatusException2.class)
    public ResponseEntity<ExceptionResponseDto> responseStatusException2Handler(ResponseStatusException2 e) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                Instant.now(),
                e.getStatusCode().value(),
                e.getErrorCode(),
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
                ErrorCode.INTERNAL_SERVER_ERROR,
                null
        );
    }
}