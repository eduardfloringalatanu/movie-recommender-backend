package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String>, ConstraintViolationBuilder {
    private static final Pattern PASSWORD_PATTERN_1 = Pattern.compile(ValidationConstants.PASSWORD_REGEX_1);
    private static final Pattern PASSWORD_PATTERN_2 = Pattern.compile(ValidationConstants.PASSWORD_REGEX_2);
    private static final Pattern PASSWORD_PATTERN_3 = Pattern.compile(ValidationConstants.PASSWORD_REGEX_3);
    private static final Pattern PASSWORD_PATTERN_4 = Pattern.compile(ValidationConstants.PASSWORD_REGEX_4);
    private static final Pattern PASSWORD_PATTERN_5 = Pattern.compile(ValidationConstants.PASSWORD_REGEX_5);

    private record ConstraintsPayload(int passwordMinSize, int passwordMaxSize, String passwordRegex1, String passwordRegex2, String passwordRegex3, String passwordRegex4, String passwordRegex5) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.PASSWORD_MIN_SIZE, ValidationConstants.PASSWORD_MAX_SIZE, ValidationConstants.PASSWORD_REGEX_1, ValidationConstants.PASSWORD_REGEX_2, ValidationConstants.PASSWORD_REGEX_3, ValidationConstants.PASSWORD_REGEX_4, ValidationConstants.PASSWORD_REGEX_5);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_BLANK_ERROR,
                    constraintsPayload);

        if (password.length() < ValidationConstants.PASSWORD_MIN_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_MIN_SIZE_ERROR,
                    constraintsPayload);

        if (password.length() > ValidationConstants.PASSWORD_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_MAX_SIZE_ERROR,
                    constraintsPayload);

        if (!PASSWORD_PATTERN_1.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_PATTERN_1_ERROR,
                    constraintsPayload);

        if (!PASSWORD_PATTERN_2.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_PATTERN_2_ERROR,
                    constraintsPayload);

        if (!PASSWORD_PATTERN_3.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_PATTERN_3_ERROR,
                    constraintsPayload);

        if (!PASSWORD_PATTERN_4.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_PATTERN_4_ERROR,
                    constraintsPayload);

        if (!PASSWORD_PATTERN_5.matcher(password).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_PATTERN_5_ERROR,
                    constraintsPayload);

        return true;
    }
}