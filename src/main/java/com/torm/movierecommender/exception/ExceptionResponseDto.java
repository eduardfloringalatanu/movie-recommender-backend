package com.torm.movierecommender.exception;

import java.time.Instant;
import java.util.List;

public record ExceptionResponseDto(
        Instant timestamp,
        int status,
        ErrorCode errorCode,
        List<ArgumentError> argumentErrors
) {
    public record ArgumentError(
            String argument,
            ErrorCode errorCode2,
            Object constraints
    ) {}
}