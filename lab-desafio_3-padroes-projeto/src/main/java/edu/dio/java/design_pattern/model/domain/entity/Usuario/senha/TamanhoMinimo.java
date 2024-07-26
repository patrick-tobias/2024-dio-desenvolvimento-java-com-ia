package edu.dio.java.design_pattern.model.domain.entity.Usuario.senha;

import org.springframework.stereotype.Component;

@Component
public class TamanhoMinimo implements _IPoliticaValidacaoSenha{

	@Override
	public void validar(String senha) {
		if(senha.length() < 8)
			throw new IllegalArgumentException("Senha deve conter pelo menos 8 caracteres.");
	}
}
