package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentifierConstraintValidator implements ConstraintValidator<Identifier, String>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int identifierMaxSize) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.IDENTIFIER_MAX_SIZE);

    @Override
    public boolean isValid(String identifier, ConstraintValidatorContext constraintValidatorContext) {
        if (identifier == null || identifier.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.IDENTIFIER_BLANK_ERROR,
                    constraintsPayload);

        String cleanedIdentifier = identifier.strip();

        if (cleanedIdentifier.length() > ValidationConstants.IDENTIFIER_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.IDENTIFIER_MAX_SIZE_ERROR,
                    constraintsPayload);

        return true;
    }
}