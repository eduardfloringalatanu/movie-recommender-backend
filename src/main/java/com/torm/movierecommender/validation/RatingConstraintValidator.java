package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingConstraintValidator implements ConstraintValidator<Rating, Short>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(Short rating, ConstraintValidatorContext constraintValidatorContext) {
        if (rating == null)
            return buildConstraintViolation(constraintValidatorContext, "RATING_NULL_ERROR");

        if (rating < 1)
            return buildConstraintViolation(constraintValidatorContext, "RATING_MIN_ERROR");

        if (rating > 10)
            return buildConstraintViolation(constraintValidatorContext, "RATING_MAX_ERROR");

        return true;
    }
}