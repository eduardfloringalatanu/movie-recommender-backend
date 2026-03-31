package com.torm.movierecommender.dto;

import com.torm.movierecommender.validation.RefreshToken;

public record LogoutRequestDto(
        @RefreshToken
        String refreshToken
) {}