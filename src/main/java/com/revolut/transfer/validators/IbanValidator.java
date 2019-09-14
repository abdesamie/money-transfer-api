package com.revolut.transfer.validators;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.ext.Provider;

@Provider
public class IbanValidator implements ConstraintValidator<ValidIban, String> {

	@Override
	public void initialize(ValidIban iban) {

	}

	@Override
	public boolean isValid(String iban, ConstraintValidatorContext context) {
		String ibanRegex = "GB\\d{2}[A-Z]{4}\\d{14}";
		return Pattern.matches(ibanRegex, iban);
	}

}
