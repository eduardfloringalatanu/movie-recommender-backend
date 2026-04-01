package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameConstraintValidator implements ConstraintValidator<Username, String>, ConstraintViolationBuilder {
    private static final Pattern USERNAME_PATTERN = Pattern.compile(ValidationConstants.USERNAME_REGEX);

    private record ConstraintsPayload(int usernameMinSize, int usernameMaxSize, String usernameRegex) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.USERNAME_MIN_SIZE, ValidationConstants.USERNAME_MAX_SIZE, ValidationConstants.USERNAME_REGEX);

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.USERNAME_BLANK_ERROR,
                    constraintsPayload);

        String cleanedUsername = username.strip();

        if (cleanedUsername.length() < ValidationConstants.USERNAME_MIN_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.USERNAME_MIN_SIZE_ERROR,
                    constraintsPayload);

        if (cleanedUsername.length() > ValidationConstants.USERNAME_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.USERNAME_MAX_SIZE_ERROR,
                    constraintsPayload);

        if (!USERNAME_PATTERN.matcher(cleanedUsername).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.USERNAME_INVALID_ERROR,
                    constraintsPayload);

        return true;
    }
}