package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class MaxYearValidator implements ConstraintValidator<MaxYear, Short> {
    @Override
    public boolean isValid(Short value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        return value <= Year.now().getValue();
    }
}