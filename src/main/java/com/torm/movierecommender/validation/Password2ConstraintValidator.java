package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Password2ConstraintValidator implements ConstraintValidator<Password2, String>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_BLANK_ERROR");

        if (password.length() > 64)
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_MAX_SIZE_ERROR");

        return true;
    }
}