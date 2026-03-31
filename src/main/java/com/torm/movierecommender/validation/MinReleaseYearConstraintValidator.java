package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class MinReleaseYearConstraintValidator implements ConstraintValidator<MinReleaseYear, Short>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(Short minReleaseYear, ConstraintValidatorContext constraintValidatorContext) {
        if (minReleaseYear == null)
            return true;

        if (minReleaseYear < 1888)
            return buildConstraintViolation(constraintValidatorContext, "MIN_RELEASE_YEAR_MIN_ERROR");

        if (minReleaseYear > Year.now().getValue())
            return buildConstraintViolation(constraintValidatorContext, "MIN_RELEASE_YEAR_MAX_ERROR");

        return true;
    }
}