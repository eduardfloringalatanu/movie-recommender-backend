package com.torm.movierecommender.controllers;

import com.torm.movierecommender.entities.RefreshTokenEntity;
import com.torm.movierecommender.repositories.RefreshTokenRepository;
import com.torm.movierecommender.security.TokenService;
import com.torm.movierecommender.validation.ValidationGroupSequences.First;
import com.torm.movierecommender.validation.ValidationGroupSequences.ValidationGroupSequence1;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RefreshAccessTokenController {
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public record RefreshTokenRequestBody(
            @NotBlank(message = "Refresh token cannot be blank.", groups = First.class)
            String refreshToken) {}
    public record RefreshTokenResponseBody(String accessToken, String refreshToken) {}

    @PostMapping("/refresh_access_token")
    @Transactional
    public RefreshTokenResponseBody refreshAccessToken(@RequestBody @Validated(ValidationGroupSequence1.class) RefreshTokenRequestBody refreshTokenRequestBody) {
        String hashedRefreshToken = tokenService.hashRefreshToken(refreshTokenRequestBody.refreshToken());

        return refreshTokenRepository.findByToken(hashedRefreshToken)
                .map(tokenService::verifyExpirationDate)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String accessToken = tokenService.generateAccessToken(user.getUsername());

                    return new RefreshTokenResponseBody(accessToken, refreshTokenRequestBody.refreshToken());
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is invalid."));
    }
}