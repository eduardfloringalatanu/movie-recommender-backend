package com.torm.movierecommender.dto;

import com.torm.movierecommender.validation.Rating;

public record RateMovieRequestDto(
        @Rating
        Short rating
) {}