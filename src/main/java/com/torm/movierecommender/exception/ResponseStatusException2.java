package com.torm.movierecommender.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseStatusException2 extends ResponseStatusException {
    @Getter
    private final ErrorCode errorCode;

    public ResponseStatusException2(HttpStatus status, ErrorCode errorCode) {
        super(status, errorCode.name());

        this.errorCode = errorCode;
    }
}