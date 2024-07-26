package edu.dio.java.design_pattern.model.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.dto.UsuarioRecord;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.senha._IPoliticaValidacaoSenha;
import edu.dio.java.design_pattern.model.repository.UsuarioRepositorio;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private Collection<_IPoliticaValidacaoSenha> validacoes;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Usuario addUsuario(UsuarioRecord record) {
		validarSenha(record.senha());
		String senha = encoder.encode(record.senha());
		
		Usuario usuario =
			Usuario
				.builder()
				.nome(record.nome())
				.email(record.email())
				.senha(senha)
				.build();
		
		return repositorio.save(usuario);
	}
	
	private void validarSenha(String senha) {
		validacoes.forEach( validacao -> validacao.validar(senha));
	}
}
