package com.torm.movierecommender.controller;

import com.torm.movierecommender.dto.RateMovieRequestDto;
import com.torm.movierecommender.service.RateMovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Validated
public class RateMovieController {
    private final RateMovieService rateMovieService;

    @PutMapping("/{movieId}/rating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rateMovie(@Positive(message = "MOVIE_ID_POSITIVE_ERROR") @PathVariable Long movieId, @Valid @RequestBody RateMovieRequestDto rateMovieRequestDto, @AuthenticationPrincipal Jwt jwt) {
        rateMovieService.rateMovie(movieId, rateMovieRequestDto, jwt);
    }
}