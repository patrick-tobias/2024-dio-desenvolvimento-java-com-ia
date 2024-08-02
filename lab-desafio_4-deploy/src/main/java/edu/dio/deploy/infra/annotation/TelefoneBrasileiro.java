package edu.dio.deploy.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = TelefoneBrasileiroValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TelefoneBrasileiro {
	String message() default "Telefone inválido ou fora de formatação. Utilize apenas números.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
