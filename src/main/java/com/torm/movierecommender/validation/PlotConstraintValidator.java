package com.torm.movierecommender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlotConstraintValidator implements ConstraintValidator<Plot, String>, ConstraintViolationBuilder {
    @Override
    public boolean isValid(String plot, ConstraintValidatorContext constraintValidatorContext) {
        if (plot == null || plot.isBlank())
            return buildConstraintViolation(constraintValidatorContext, "PLOT_BLANK_ERROR");

        return true;
    }
}