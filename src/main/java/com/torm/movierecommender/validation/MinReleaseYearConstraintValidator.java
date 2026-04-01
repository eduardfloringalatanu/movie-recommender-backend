package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class MinReleaseYearConstraintValidator implements ConstraintValidator<MinReleaseYear, Short>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int minReleaseYearMin, int minReleaseYearMax) {}

    @Override
    public boolean isValid(Short minReleaseYear, ConstraintValidatorContext constraintValidatorContext) {
        if (minReleaseYear == null)
            return true;

        int currentYear = Year.now().getValue();

        ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.MIN_RELEASE_YEAR_MIN, currentYear);

        if (minReleaseYear < ValidationConstants.MIN_RELEASE_YEAR_MIN)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.MIN_RELEASE_YEAR_MIN_ERROR,
                    constraintsPayload);

        if (minReleaseYear > currentYear)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.MIN_RELEASE_YEAR_MAX_ERROR,
                    constraintsPayload);

        return true;
    }
}