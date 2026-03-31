package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidatorContext;

public interface ConstraintViolationBuilder {
    default boolean buildConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();

        return false;
    }
}