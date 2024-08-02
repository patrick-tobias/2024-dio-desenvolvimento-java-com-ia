package edu.dio.deploy.model.entity.Contato.dto;

import edu.dio.deploy.model.entity.Contato.Contato;

public record ContatoListarRecord(
		String id,
		String nome,
		String email,
		String telefone,
		String cep,
		String cidade,
		String estado) {
	
	public ContatoListarRecord(Contato c) {
		this(c.getId(), c.getNome(), c.getEmail(), c.getTelefone(), c.getCep(), c.getCidade(), c.getEstado());
	}
}
