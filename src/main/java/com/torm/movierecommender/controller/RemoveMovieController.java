package com.torm.movierecommender.controller;

import com.torm.movierecommender.service.RemoveMovieService;
import com.torm.movierecommender.validation.MovieId;
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
public class RemoveMovieController {
    private final RemoveMovieService removeMovieService;

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMovie(@MovieId @PathVariable Long movieId, @AuthenticationPrincipal Jwt jwt) {
        removeMovieService.removeMovie(movieId, jwt);
    }
}