package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import com.torm.movierecommender.util.GenreUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class GenresConstraintValidator implements ConstraintValidator<Genres, Set<String>>, ConstraintViolationBuilder {
    private record ConstraintsPayload(Set<String> allowedGenres) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(GenreUtils.GENRE_DICTIONARY.keySet());

    @Override
    public boolean isValid(Set<String> genres, ConstraintValidatorContext constraintValidatorContext) {
        if (genres == null || genres.isEmpty())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.GENRES_EMPTY_ERROR,
                    constraintsPayload);

        for (String genre : genres) {
            if (genre == null || genre.isBlank())
                return buildConstraintViolation(constraintValidatorContext,
                        ErrorCode.GENRES_ITEMS_BLANK_ERROR,
                        constraintsPayload);

            String cleanedGenre = genre.strip().toLowerCase();

            if (!GenreUtils.GENRE_DICTIONARY.containsKey(cleanedGenre))
                return buildConstraintViolation(constraintValidatorContext,
                        ErrorCode.GENRES_ITEMS_INVALID_ERROR,
                        constraintsPayload);
        }

        return true;
    }
}