package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class DirectorsConstraintValidator implements ConstraintValidator<Directors, Set<String>>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(Set<String> directors, ConstraintValidatorContext constraintValidatorContext) {
        if (directors == null || directors.isEmpty())
            return buildConstraintViolation(constraintValidatorContext, "DIRECTORS_EMPTY_ERROR");

        for (String director : directors)
            if (director == null || director.isBlank()) {
                return buildConstraintViolation(constraintValidatorContext, "DIRECTORS_ITEMS_BLANK_ERROR");
        }

        return true;
    }
}