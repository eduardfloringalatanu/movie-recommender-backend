package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class DirectorsConstraintValidator implements ConstraintValidator<Directors, Set<String>>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int directorsMaxLength) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.DIRECTORS_MAX_SIZE);

    @Override
    public boolean isValid(Set<String> directors, ConstraintValidatorContext constraintValidatorContext) {
        if (directors == null || directors.isEmpty())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.DIRECTORS_EMPTY_ERROR,
                    constraintsPayload);

        int directorsLength = 0;

        for (String director : directors) {
            if (director == null || director.isBlank())
                return buildConstraintViolation(constraintValidatorContext,
                        ErrorCode.DIRECTORS_ITEMS_BLANK_ERROR,
                        constraintsPayload);

            String cleanedDirector = director.strip()
                    .replaceAll("\\s+", " ");

            directorsLength += cleanedDirector.length();
        }

        directorsLength += (directors.size() - 1);

        if (directorsLength > ValidationConstants.DIRECTORS_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.DIRECTORS_MAX_SIZE_ERROR,
                    constraintsPayload);

        return true;
    }
}