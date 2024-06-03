package com.planner.planner.Common.Validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotListEmptyValidator.class)
public @interface NotListEmpty {
	String message() default "비어있는 리스트입니다.";
	Class[] groups() default {};
	Class[] payload() default {};
}
