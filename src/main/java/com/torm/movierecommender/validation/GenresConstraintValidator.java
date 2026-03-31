package com.torm.movierecommender.validation;

import com.torm.movierecommender.util.GenreUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class GenresConstraintValidator implements ConstraintValidator<Genres, Set<String>>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(Set<String> genres, ConstraintValidatorContext constraintValidatorContext) {
        if (genres == null || genres.isEmpty())
            return buildConstraintViolation(constraintValidatorContext, "GENRES_EMPTY_ERROR");

        for (String genre : genres) {
            if (genre == null || genre.isBlank())
                return buildConstraintViolation(constraintValidatorContext, "GENRES_ITEMS_BLANK_ERROR");

            String cleanedGenre = genre.strip().toLowerCase();

            if (!GenreUtils.GENRE_DICTIONARY.containsKey(cleanedGenre))
                return buildConstraintViolation(constraintValidatorContext, "GENRES_ITEMS_INVALID_ERROR");
        }

        return true;
    }
}