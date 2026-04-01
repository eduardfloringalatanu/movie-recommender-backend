package com.torm.movierecommender.validation;

import com.torm.movierecommender.exception.ErrorCode;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public interface ConstraintViolationBuilder {
    default boolean buildConstraintViolation(ConstraintValidatorContext constraintValidatorContext, ErrorCode errorCode) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(errorCode.name())
                .addConstraintViolation();

        return false;
    }

    default boolean buildConstraintViolation(ConstraintValidatorContext constraintValidatorContext, ErrorCode errorCode, Object payload) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)
                .withDynamicPayload(payload)
                .buildConstraintViolationWithTemplate(errorCode.name())
                .addConstraintViolation();

        return false;
    }
}