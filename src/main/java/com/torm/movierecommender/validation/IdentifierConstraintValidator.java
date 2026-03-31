package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentifierConstraintValidator implements ConstraintValidator<Identifier, String>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(String identifier, ConstraintValidatorContext constraintValidatorContext) {
        if (identifier == null || identifier.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "IDENTIFIER_BLANK_ERROR");

        String cleanedIdentifier = identifier.strip();

        if (cleanedIdentifier.length() > 254)
            return buildConstraintViolation(constraintValidatorContext, "IDENTIFIER_MAX_SIZE_ERROR");

        return true;
    }
}