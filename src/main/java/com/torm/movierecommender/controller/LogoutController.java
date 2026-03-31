package com.torm.movierecommender.controller;

import com.torm.movierecommender.dto.LogoutRequestDto;
import com.torm.movierecommender.service.LogoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutService logoutService;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody LogoutRequestDto logoutRequestDto, @AuthenticationPrincipal Jwt jwt) {
        logoutService.logout(logoutRequestDto, jwt);
    }
}