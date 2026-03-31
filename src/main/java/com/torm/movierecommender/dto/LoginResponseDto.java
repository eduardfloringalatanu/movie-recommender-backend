package com.torm.movierecommender.dto;

public record LoginResponseDto(
        String accessToken,

        String refreshToken
) {}