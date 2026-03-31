package com.torm.movierecommender.dto;

public record RegisterResponseDto(
        String accessToken,

        String refreshToken
) {}