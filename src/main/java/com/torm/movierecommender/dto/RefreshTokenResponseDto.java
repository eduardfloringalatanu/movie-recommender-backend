package com.torm.movierecommender.dto;

public record RefreshTokenResponseDto(
        String accessToken,

        String refreshToken
) {}