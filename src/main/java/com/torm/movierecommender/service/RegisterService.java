package com.torm.movierecommender.service;

import com.torm.movierecommender.dto.RegisterRequestDto;
import com.torm.movierecommender.dto.RegisterResponseDto;
import com.torm.movierecommender.entity.UserEntity;
import com.torm.movierecommender.repository.UserRepository;
import com.torm.movierecommender.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        String username = registerRequestDto.username()
                .strip()
                .toLowerCase();

        if (userRepository.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "USERNAME_CONFLICT_ERROR");

        String email = registerRequestDto.email()
                .strip()
                .toLowerCase();

        if (userRepository.findByEmail(email).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL_CONFLICT_ERROR");

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));

        userRepository.save(user);

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        String refreshToken = tokenService.generateRefreshToken(user.getUserId());

        return new RegisterResponseDto(accessToken, refreshToken);
    }
}