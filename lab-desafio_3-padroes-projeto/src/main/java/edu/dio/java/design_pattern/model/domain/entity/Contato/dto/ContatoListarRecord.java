package edu.dio.java.design_pattern.model.domain.entity.Contato.dto;

import edu.dio.java.design_pattern.model.domain.entity.Contato.Contato;

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
