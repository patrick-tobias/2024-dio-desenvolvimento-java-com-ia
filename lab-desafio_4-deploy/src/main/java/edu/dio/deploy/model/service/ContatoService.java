package edu.dio.deploy.model.service;

import java.util.Collection;

import edu.dio.deploy.model.entity.Contato.Contato;
import edu.dio.deploy.model.entity.Contato.dto.ContatoRecord;
import jakarta.validation.Valid;

public interface ContatoService {
	Contato adicionarContato(@Valid ContatoRecord record);
	Collection<Contato> listarContatos();
	Contato visualizarContato(String id);
	Contato atualizarContato(ContatoRecord record);
	void deletarContato(String id);
}
