package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String>, ConstraintViolationBuilder {
    private static final Pattern PATTERN1 = Pattern.compile(".*[a-z].*");
    private static final Pattern PATTERN2 = Pattern.compile(".*[A-Z].*");
    private static final Pattern PATTERN3 = Pattern.compile(".*[0-9].*");
    private static final Pattern PATTERN4 = Pattern.compile(".*[~!@#$%^&*()_+`\\-=\\[\\]\\\\{}|;':\",./<>?].*");
    private static final Pattern PATTERN5 = Pattern.compile("^[a-zA-Z0-9~!@#$%^&*()_+`\\-=\\[\\]\\\\{}|;':\",./<>?]+$");

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_BLANK_ERROR");

        if (password.length() < 8)
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_MIN_SIZE_ERROR");

        if (password.length() > 64)
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_MAX_SIZE_ERROR");

        if (!PATTERN1.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_PATTERN_ERROR_1");

        if (!PATTERN2.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_PATTERN_ERROR_2");

        if (!PATTERN3.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_PATTERN_ERROR_3");

        if (!PATTERN4.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_PATTERN_ERROR_4");

        if (!PATTERN5.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext, "PASSWORD_PATTERN_ERROR_5");

        return true;
    }
}