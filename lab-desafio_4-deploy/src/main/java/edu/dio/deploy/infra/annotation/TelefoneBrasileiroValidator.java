package edu.dio.deploy.infra.annotation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneBrasileiroValidator implements ConstraintValidator<TelefoneBrasileiro, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null)
			return false;
		
		return Pattern.matches("\\d{10}", value) || Pattern.matches("\\d{11}", value);
	}

}
