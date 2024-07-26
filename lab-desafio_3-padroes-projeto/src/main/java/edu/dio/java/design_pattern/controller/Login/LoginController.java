package edu.dio.java.design_pattern.controller.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dio.java.design_pattern.infra.config.seguranca.TokenJwtService;
import edu.dio.java.design_pattern.model.adapter.UserDetailsParaUsuarioAdapter;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/login")
public class LoginController {
	
    @Autowired
    private AuthenticationManager auhenticationManager;
	
	@Autowired
	private TokenJwtService tokenService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody @Valid LoginRecord login){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(login.email(), login.senha());
		
		Authentication autenticado = 
				auhenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		Usuario usuario = 
				((UserDetailsParaUsuarioAdapter) autenticado.getPrincipal())
					.getUsuario();
		
		String token = tokenService.gerarToken(usuario);
		
		return ResponseEntity.ok(new TokenJwtRecord(token));
	}
	

	public record LoginRecord(
			@NotBlank
			@Email
			String email,
			
			@NotBlank
			String senha
			) {}
	
	public record TokenJwtRecord(
			String token
			) {}
	
}
