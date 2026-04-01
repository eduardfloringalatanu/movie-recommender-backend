package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MovieIdConstraintValidator implements ConstraintValidator<MovieId, Long>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int movieIdMin) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.MOVIE_ID_MIN);

    @Override
    public boolean isValid(Long movieId, ConstraintValidatorContext constraintValidatorContext) {
        /*if (movieId == null)
            return buildConstraintViolation(constraintValidatorContext,
            ErrorCode.MOVIE_ID_NULL_ERROR,
            constraintsPayload);*/

        if (movieId < ValidationConstants.MOVIE_ID_MIN)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.MOVIE_ID_MIN_ERROR,
                    constraintsPayload);

        return true;
    }
}