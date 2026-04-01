package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlotConstraintValidator implements ConstraintValidator<Plot, String>, ConstraintViolationBuilder {
    private record ConstraintsPayload(int plotMaxSize) {}
    private static final ConstraintsPayload constraintsPayload = new ConstraintsPayload(ValidationConstants.PLOT_MAX_SIZE);

    @Override
    public boolean isValid(String plot, ConstraintValidatorContext constraintValidatorContext) {
        if (plot == null || plot.isBlank())
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PLOT_BLANK_ERROR,
                    constraintsPayload);

        if (plot.length() > ValidationConstants.PLOT_MAX_SIZE)
            return buildConstraintViolation(constraintValidatorContext,
                    ErrorCode.PLOT_MAX_SIZE_ERROR,
                    constraintsPayload);

        return true;
    }
}