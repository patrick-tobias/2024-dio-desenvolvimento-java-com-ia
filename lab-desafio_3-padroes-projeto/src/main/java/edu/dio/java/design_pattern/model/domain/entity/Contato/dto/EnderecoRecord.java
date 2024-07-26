package edu.dio.java.design_pattern.model.domain.entity.Contato.dto;

public record EnderecoRecord(
		String cep,
		String localidade,
		String uf
		) {

}
