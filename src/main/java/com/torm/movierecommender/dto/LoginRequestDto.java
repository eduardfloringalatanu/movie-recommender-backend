package com.torm.movierecommender.dto;

import com.torm.movierecommender.validation.Identifier;
import com.torm.movierecommender.validation.Password2;

public record LoginRequestDto(
        @Identifier
        String identifier,

        @Password2
        String password
) {}