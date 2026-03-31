package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class Email2ConstraintValidator implements ConstraintValidator<Email2, String>, ConstraintViolationBuilder {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9](?:[._-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9](?:[.-]?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "EMAIL_BLANK_ERROR");

        String cleanedEmail = email.strip();

        if (cleanedEmail.length() < 6)
            return buildConstraintViolation(constraintValidatorContext, "EMAIL_MIN_SIZE_ERROR");

        if (cleanedEmail.length() > 320)
            return buildConstraintViolation(constraintValidatorContext, "EMAIL_MAX_SIZE_ERROR_2");

        if (!EMAIL_PATTERN.matcher(cleanedEmail).matches())
            return buildConstraintViolation(constraintValidatorContext, "EMAIL_INVALID_ERROR");

        return true;
    }
}