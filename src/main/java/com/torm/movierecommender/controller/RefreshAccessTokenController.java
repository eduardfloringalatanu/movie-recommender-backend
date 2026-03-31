package com.torm.movierecommender.controller;

import com.torm.movierecommender.dto.RefreshTokenRequestDto;
import com.torm.movierecommender.dto.RefreshTokenResponseDto;
import com.torm.movierecommender.service.RefreshAccessTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshAccessTokenController {
    private final RefreshAccessTokenService refreshAccessTokenService;

    @PostMapping("/refresh")
    public RefreshTokenResponseDto refreshAccessToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return refreshAccessTokenService.refreshAccessToken(refreshTokenRequestDto);
    }
}