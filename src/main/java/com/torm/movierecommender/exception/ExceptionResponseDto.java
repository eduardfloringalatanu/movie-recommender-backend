package com.torm.movierecommender.exception;

import java.time.Instant;
import java.util.List;

public record ExceptionResponseDto(
        Instant timestamp,
        int status,
        String errorType,
        List<ArgumentError> argumentErrors
) {
    public record ArgumentError(
            String argument,
            String errorCode
    ) {}
}