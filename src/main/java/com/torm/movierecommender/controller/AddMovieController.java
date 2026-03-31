package com.torm.movierecommender.controller;

import com.torm.movierecommender.dto.AddMovieRequestDto;
import com.torm.movierecommender.service.AddMovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class AddMovieController {
    private final AddMovieService addMovieService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@Valid @RequestBody AddMovieRequestDto addMovieRequestDto, @AuthenticationPrincipal Jwt jwt) {
        addMovieService.addMovie(addMovieRequestDto, jwt);
    }
}