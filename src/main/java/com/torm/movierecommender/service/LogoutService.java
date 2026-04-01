package com.torm.movierecommender.service;

import com.torm.movierecommender.dto.LogoutRequestDto;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.exception.ErrorCode;
import com.torm.movierecommender.exception.ResponseStatusException2;
import com.torm.movierecommender.repository.RefreshTokenRepository;
import com.torm.movierecommender.repository.UserRepository;
import com.torm.movierecommender.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void logout(LogoutRequestDto logoutRequestDto, Jwt jwt) {
        String username = jwt.getSubject();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException2(HttpStatus.UNAUTHORIZED, ErrorCode.USER_UNAUTHORIZED_ERROR));

        refreshTokenRepository.findByToken(tokenService.hashRefreshToken(logoutRequestDto.refreshToken()))
                .ifPresent(token -> {
                    if (token.getUser().getUserId().equals(user.getUserId()))
                        refreshTokenRepository.delete(token);
                });
    }
}