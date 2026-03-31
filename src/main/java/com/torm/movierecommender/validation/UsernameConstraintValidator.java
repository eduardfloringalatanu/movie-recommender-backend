package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameConstraintValidator implements ConstraintValidator<Username, String>, ConstraintViolationBuilder {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9](?:[._-]?[a-zA-Z0-9]+)*$");

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "USERNAME_BLANK_ERROR");

        String cleanedUsername = username.strip();

        if (cleanedUsername.length() < 3)
            return buildConstraintViolation(constraintValidatorContext, "USERNAME_SIZE_ERROR_1");

        if (cleanedUsername.length() > 32)
            return buildConstraintViolation(constraintValidatorContext, "USERNAME_SIZE_ERROR_2");

        if (!USERNAME_PATTERN.matcher(cleanedUsername).matches())
            return buildConstraintViolation(constraintValidatorContext, "USERNAME_INVALID_ERROR");

        return true;
    }
}