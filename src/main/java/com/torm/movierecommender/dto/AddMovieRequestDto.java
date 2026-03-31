package com.torm.movierecommender.dto;

import com.torm.movierecommender.validation.*;
import java.util.Set;

public record AddMovieRequestDto(
        @Title
        String title,

        @ReleaseYear
        Short releaseYear,

        @Directors
        Set<String> directors,

        @Genres
        Set<String> genres,

        @Plot
        String plot
) {}