package edu.dio.deploy.infra.annotation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepBrasileiroValidator implements ConstraintValidator<CepBrasileiro, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null)
			return false;
		
		return Pattern.matches("\\d{8}", value);
	}

}
