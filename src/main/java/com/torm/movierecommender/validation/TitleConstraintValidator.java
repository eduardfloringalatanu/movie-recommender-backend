package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleConstraintValidator implements ConstraintValidator<Title, String>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null || title.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "TITLE_BLANK_ERROR");

        return true;
    }
}