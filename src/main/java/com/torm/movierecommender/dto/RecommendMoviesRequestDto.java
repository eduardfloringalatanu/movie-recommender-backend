package com.torm.movierecommender.dto;

import com.torm.movierecommender.validation.ExcludedGenres;
import com.torm.movierecommender.validation.MinReleaseYear;
import java.util.Set;

public record RecommendMoviesRequestDto(
        @ExcludedGenres
        Set<String> excludedGenres,

        @MinReleaseYear
        Short minReleaseYear
) {}