package com.planner.planner.Common.Validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = ValidCoordinateValidator.class)
public @interface ValidCoordinate {
	String message() default "잘못된 좌표입니다.";
	Class[] groups() default {};
	Class[] payload() default {};
}
