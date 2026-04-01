package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleConstraintValidator implements ConstraintValidator<Title, String>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int titleMaxSize) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.TITLE_MAX_SIZE);

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null || title.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.TITLE_BLANK_ERROR,
                    constraintsPayload);

        if (title.strip()
                .replaceAll("\\s+", " ")
                .length() > ValidationConstants.TITLE_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.TITLE_MAX_SIZE_ERROR,
                    constraintsPayload);

        return true;
    }
}