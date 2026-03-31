package com.torm.movierecommender.validation;

import com.torm.movierecommender.util.GenreUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class ExcludedGenresConstraintValidator implements ConstraintValidator<ExcludedGenres, Set<String>>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(Set<String> excludedGenres, ConstraintValidatorContext constraintValidatorContext) {
        if (excludedGenres == null || excludedGenres.isEmpty())
            return true;

        for (String excludedGenre : excludedGenres) {
            if (excludedGenre == null || excludedGenre.isBlank())
                return buildConstraintViolation(constraintValidatorContext, "EXCLUDED_GENRES_ITEMS_BLANK_ERROR");

            String cleanedGenre = excludedGenre.strip().toLowerCase();

            if (!GenreUtils.GENRE_DICTIONARY.containsKey(cleanedGenre))
                return buildConstraintViolation(constraintValidatorContext, "EXCLUDED_GENRES_ITEMS_INVALID_ERROR");
        }

        return true;
    }
}