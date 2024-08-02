package edu.dio.deploy.model.entity.Contato.dto;

import edu.dio.deploy.infra.annotation.CepBrasileiro;
import edu.dio.deploy.infra.annotation.TelefoneBrasileiro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContatoRecord(
		
		String id,
		
		@NotBlank(message = "Nome não deve ser vazio.")
		String nome,
		
		@NotBlank(message = "E-mail não deve ser vazio.")
		@Email(message = "E-mail inválido.")
		String email,
		
		@NotBlank(message = "Telefone não deve ser vazio.")
		@TelefoneBrasileiro
		String telefone,
		
		@NotBlank(message = "CEP não deve ser vazio.")
		@CepBrasileiro
		String cep
		) {

}
