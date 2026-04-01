package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RefreshTokenConstraintValidator implements ConstraintValidator<RefreshToken, String>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(String refreshToken, ConstraintValidatorContext constraintValidatorContext) {
        if (refreshToken == null || refreshToken.isBlank())
            return buildConstraintViolation(constraintValidatorContext, ErrorCode.REFRESH_TOKEN_BLANK_ERROR);

        return true;
    }
}