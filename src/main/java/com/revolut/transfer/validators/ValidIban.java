package com.revolut.transfer.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom annotation for iban validation
 * 
 * @author ABDESSAMIE
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { IbanValidator.class })
public @interface ValidIban {
	String message() default "INVALID IBAN FORMAT";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
