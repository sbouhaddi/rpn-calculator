package com.sbouhaddi.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperandValidationImpl.class)
public @interface OperandValidation {

	String message() default "should be in +,-,/,*,^ ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
