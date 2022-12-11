package com.sbouhaddi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OperandValidationImpl implements ConstraintValidator<OperandValidation, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String ops = "+,-,/,*,^";
		value = value.trim().toUpperCase();

		if (ops.contains(value)) {
			return true;
		}

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(
				String.format("Operand (%s) is not in (+,-,/,*,^), which is invalid.", value)).addConstraintViolation();
		return false;
	}

}
