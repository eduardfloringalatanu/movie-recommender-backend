package com.torm.movierecommender.dto;

import com.torm.movierecommender.validation.Email2;
import com.torm.movierecommender.validation.Password;
import com.torm.movierecommender.validation.Username;

public record RegisterRequestDto(
        @Username
        String username,

        @Email2
        String email,

        @Password
        String password
) {}