package com.torm.movierecommender.service;

import com.torm.movierecommender.dto.LoginRequestDto;
import com.torm.movierecommender.dto.LoginResponseDto;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.exception.ErrorCode;
import com.torm.movierecommender.exception.ResponseStatusException2;
import com.torm.movierecommender.repository.UserRepository;
import com.torm.movierecommender.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String identifier = loginRequestDto.identifier()
                .strip()
                .toLowerCase();

        UserEntity user = userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .orElseThrow(() ->
                        new ResponseStatusException2(HttpStatus.UNAUTHORIZED, ErrorCode.CREDENTIALS_INVALID_ERROR));

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword()))
            throw new ResponseStatusException2(HttpStatus.UNAUTHORIZED, ErrorCode.CREDENTIALS_INVALID_ERROR);

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken(user.getUserId());

        return new LoginResponseDto(accessToken, refreshToken);
    }
}