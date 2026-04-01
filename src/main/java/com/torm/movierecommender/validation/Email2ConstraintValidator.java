package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class Email2ConstraintValidator implements ConstraintValidator<Email2, String>, ConstraintViolationBuilder {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(ValidationConstants.EMAIL_REGEX);

    private record ConstraintsPayload(int emailMinSize, int emailMaxSize, String emailRegex) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.EMAIL_MIN_SIZE, ValidationConstants.EMAIL_MAX_SIZE, ValidationConstants.EMAIL_REGEX);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.EMAIL_BLANK_ERROR,
                    constraintsPayload);

        String cleanedEmail = email.strip();

        if (cleanedEmail.length() < ValidationConstants.EMAIL_MIN_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.EMAIL_MIN_SIZE_ERROR,
                    constraintsPayload);

        if (cleanedEmail.length() > ValidationConstants.EMAIL_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.EMAIL_MAX_SIZE_ERROR,
                    constraintsPayload);

        if (!EMAIL_PATTERN.matcher(cleanedEmail).matches())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.EMAIL_INVALID_ERROR,
                    constraintsPayload);

        return true;
    }
}