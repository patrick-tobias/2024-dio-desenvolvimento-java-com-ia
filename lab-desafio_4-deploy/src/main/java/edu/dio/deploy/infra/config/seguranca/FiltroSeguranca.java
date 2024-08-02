package edu.dio.deploy.infra.config.seguranca;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.dio.deploy.controller.Login.LoginService;
import edu.dio.deploy.model.adapter.UserDetailsParaUsuarioAdapter;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {
	
	@Autowired
	private TokenJwtService jwtService;
	
	@Autowired
	private LoginService service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = recuperarToken(request);
		
		try {
			if(token != null) {
				String usuarioEmailSubject = jwtService.getSubject(token);
				
				UserDetailsParaUsuarioAdapter usuario = 
						(UserDetailsParaUsuarioAdapter) service.loadUserByUsername(usuarioEmailSubject);
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			filterChain.doFilter(request, response);
				
		} catch(SignatureException e) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("Erro ao processar assinatura JWT.");
		}
	}
	
	private String recuperarToken(HttpServletRequest request) {
		String autorizacao = request.getHeader("Authorization");
		
		if(autorizacao != null)
			return autorizacao.replace("Bearer ", "");

		return null;
	}

}
