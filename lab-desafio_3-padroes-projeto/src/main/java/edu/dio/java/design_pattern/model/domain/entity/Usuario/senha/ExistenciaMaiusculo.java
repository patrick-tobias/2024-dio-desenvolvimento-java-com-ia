package edu.dio.java.design_pattern.model.domain.entity.Usuario.senha;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ExistenciaMaiusculo implements _IPoliticaValidacaoSenha {

	/**
     * Validate the password to ensure it contains at least one uppercase character.
     * 
     * @param senha is the password to be validated
     * @throws IllegalArgumentException if the password does not contain at least one uppercase character
     */
    @Override
    public void validar(String senha) {
        if (!Pattern.matches(".*[A-Z].*", senha)) {
            throw new IllegalArgumentException("Senha deve conter pelo menos um caractere maiúsculo.");
        }
    }

}
