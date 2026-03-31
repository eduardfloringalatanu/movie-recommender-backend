package com.torm.movierecommender.controller;

import com.torm.movierecommender.dto.RecommendMoviesRequestDto;
import com.torm.movierecommender.service.RecommendMoviesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class RecommendMoviesController {
    private final RecommendMoviesService recommendMoviesService;

    @PostMapping(value = "/recommend", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> recommendMovies(@Valid @RequestBody RecommendMoviesRequestDto recommendMoviesRequestDto, @AuthenticationPrincipal Jwt jwt) {
        return recommendMoviesService.recommendMovies(recommendMoviesRequestDto, jwt);
    }
}