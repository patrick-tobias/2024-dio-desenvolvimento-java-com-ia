package edu.dio.deploy.model.service.implementacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.dio.deploy.infra.exception.RegraNegocioException;
import edu.dio.deploy.model.adapter.UserDetailsParaUsuarioAdapter;
import edu.dio.deploy.model.entity.Usuario.Usuario;
import edu.dio.deploy.model.entity.Usuario.dto.UsuarioRecord;
import edu.dio.deploy.model.entity.Usuario.senha._IPoliticaValidacaoSenha;
import edu.dio.deploy.model.repository.ContatoRepositorio;
import edu.dio.deploy.model.repository.UsuarioRepositorio;
import edu.dio.deploy.model.service.UsuarioService;

@Service
public class UsuarioServiceImplementacao implements UsuarioService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private ContatoRepositorio contatoRepositorio;
	
	@Autowired
	private Collection<_IPoliticaValidacaoSenha> validacoes;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private Usuario getUsuarioAutenticado() {
		return ((UserDetailsParaUsuarioAdapter) 
				SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal()
				)
				.getUsuario();
	}
	
	@Override
	public Usuario addUsuario(UsuarioRecord record) {
		if(repositorio.findByEmail(record.email()).isPresent())
			throw new RegraNegocioException("Usuário já cadastrado no sistema.");
		
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
	
	@Override
	public void removerUsuario(String id) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();

		if(!usuarioAutenticado.getId().equals(id))
			throw new AccessDeniedException("Somente o usuário autenticado pode remover a própria conta.");
		
		contatoRepositorio.deleteAllByUsuario(usuarioAutenticado);
		repositorio.delete(usuarioAutenticado);
	}
}
