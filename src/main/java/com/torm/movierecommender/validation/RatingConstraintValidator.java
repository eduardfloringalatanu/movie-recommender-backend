package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingConstraintValidator implements ConstraintValidator<Rating, Short>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int ratingMin, int ratingMax) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.RATING_MIN, ValidationConstants.RATING_MAX);

    @Override
    public boolean isValid(Short rating, ConstraintValidatorContext constraintValidatorContext) {
        if (rating == null)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.RATING_NULL_ERROR,
                    constraintsPayload);

        if (rating < ValidationConstants.RATING_MIN)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.RATING_MIN_ERROR,
                    constraintsPayload);

        if (rating > ValidationConstants.RATING_MAX)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.RATING_MAX_ERROR,
                    constraintsPayload);

        return true;
    }
}