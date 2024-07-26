package edu.dio.java.design_pattern.model.domain.entity.Usuario.dto;

import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioListarRecord(

		@NotBlank
		String nome,
		
		@NotBlank
		@Email
		String email) {
	
	public UsuarioListarRecord (Usuario usuario) {
		this(usuario.getNome(), usuario.getEmail());
	}
	 
}

