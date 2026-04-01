package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Password2ConstraintValidator implements ConstraintValidator<Password2, String>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int passwordMaxSize) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.PASSWORD_MAX_SIZE);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_BLANK_ERROR,
                    constraintsPayload);

        if (password.length() > ValidationConstants.PASSWORD_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PASSWORD_MAX_SIZE_ERROR,
                    constraintsPayload);

        return true;
    }
}