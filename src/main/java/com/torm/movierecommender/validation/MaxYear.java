package com.torm.movierecommender.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = MaxYearValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxYear {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}