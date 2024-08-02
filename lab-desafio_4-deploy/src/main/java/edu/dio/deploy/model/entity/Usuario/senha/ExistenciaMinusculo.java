package edu.dio.deploy.model.entity.Usuario.senha;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ExistenciaMinusculo implements _IPoliticaValidacaoSenha {

	/**
     * Validate the password to ensure it contains at least one lowercase character.
     * 
     * @param senha is the password to be validated
     * @throws IllegalArgumentException if the password does not contain at least one lowercase character
     */
    @Override
    public void validar(String senha) {
        if (!Pattern.matches(".*[a-z].*", senha)) {
            throw new IllegalArgumentException("Senha deve conter pelo menos um caractere minúsculo.");
        }
    }

}
