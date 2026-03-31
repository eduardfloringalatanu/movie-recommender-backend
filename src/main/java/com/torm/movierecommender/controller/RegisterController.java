package com.torm.movierecommender.controller;

import com.torm.movierecommender.dto.RegisterRequestDto;
import com.torm.movierecommender.dto.RegisterResponseDto;
import com.torm.movierecommender.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return registerService.register(registerRequestDto);
    }
}