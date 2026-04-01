package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class ReleaseYearConstraintValidator implements ConstraintValidator<ReleaseYear, Short>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int releaseYearMin, int releaseYearMax) {}

    @Override
    public boolean isValid(Short releaseYear, ConstraintValidatorContext constraintValidatorContext) {
        int currentYear = Year.now().getValue();

        ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.RELEASE_YEAR_MIN, currentYear);

        if (releaseYear == null)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.RELEASE_YEAR_NULL_ERROR,
                    constraintsPayload);

        if (releaseYear < ValidationConstants.RELEASE_YEAR_MIN)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.RELEASE_YEAR_MIN_ERROR,
                    constraintsPayload);

        if (releaseYear > Year.now().getValue())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.RELEASE_YEAR_MAX_ERROR,
                    constraintsPayload);

        return true;
    }
}