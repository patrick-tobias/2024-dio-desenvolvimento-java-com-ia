package edu.dio.java.design_pattern.controller.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.dio.java.design_pattern.model.adapter.UserDetailsParaUsuarioAdapter;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import edu.dio.java.design_pattern.model.repository.UsuarioRepositorio;

@Service
public class LoginService implements UserDetailsService{

	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario =
		repositorio
			.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail fornecido."));
		
		return new UserDetailsParaUsuarioAdapter(usuario);
	}

}
