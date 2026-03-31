package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class ReleaseYearConstraintValidator implements ConstraintValidator<ReleaseYear, Short>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(Short releaseYear, ConstraintValidatorContext constraintValidatorContext) {
        if (releaseYear == null)
            return buildConstraintViolation(constraintValidatorContext, "RELEASE_YEAR_NULL_ERROR");

        if (releaseYear < 1888)
            return buildConstraintViolation(constraintValidatorContext, "RELEASE_YEAR_MIN_ERROR");

        if (releaseYear > Year.now().getValue())
            return buildConstraintViolation(constraintValidatorContext, "RELEASE_YEAR_MAX_ERROR");

        return true;
    }
}