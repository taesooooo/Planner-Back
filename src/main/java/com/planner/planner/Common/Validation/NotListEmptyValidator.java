package com.planner.planner.Common.Validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotListEmptyValidator implements ConstraintValidator<NotListEmpty, List<?>> {

	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		return value.isEmpty() ? false : true;
	}
	
}
