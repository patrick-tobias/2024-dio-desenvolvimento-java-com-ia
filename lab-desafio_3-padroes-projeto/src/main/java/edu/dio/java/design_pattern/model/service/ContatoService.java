package edu.dio.java.design_pattern.model.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.dio.java.design_pattern.model.adapter.UserDetailsParaUsuarioAdapter;
import edu.dio.java.design_pattern.model.domain.entity.Contato.Contato;
import edu.dio.java.design_pattern.model.domain.entity.Contato.dto.ContatoRecord;
import edu.dio.java.design_pattern.model.domain.entity.Contato.dto.EnderecoRecord;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import edu.dio.java.design_pattern.model.repository.ContatoRepositorio;
import jakarta.validation.Valid;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepositorio repositorio;
	
	@Autowired
	private ViaCepService viaCepService;
	
	private Usuario getUsuarioAutenticado() {
		return ((UserDetailsParaUsuarioAdapter) 
				SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal()
				)
				.getUsuario();
	}

	public Contato adicionarContato(@Valid ContatoRecord record) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		
		EnderecoRecord consultarCep = viaCepService.consultarCep(record.cep());
		
		Contato contato =
			Contato
				.builder()
				.usuario(usuarioAutenticado)
				.nome(record.nome())
				.email(record.email())
				.telefone(record.telefone())
				.cep(record.cep())
				.cidade(consultarCep.localidade())
				.estado(consultarCep.uf())
				.build();
		
		return repositorio.save(contato);
	}
	
	public Collection<Contato> listarContatos() {
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		return repositorio.findAllByUsuario(usuarioAutenticado);
	}

	public Contato visualizarContato(String id) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();

		Optional<Contato> contatoOptional = repositorio.findByUsuarioAndId(usuarioAutenticado, id);
		if(contatoOptional.isEmpty())
			throw new IllegalArgumentException("Nenhum contato com o id fornecido foi encontrado na lista de contatos.");
		
		return contatoOptional.get();
	}

	public Contato atualizarContato(ContatoRecord record) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		
		Optional<Contato> contatoOptional = repositorio.findByUsuarioAndId(usuarioAutenticado, record.id());
		if(contatoOptional.isEmpty())
			throw new IllegalArgumentException("Nenhum contato com o id fornecido foi encontrado na lista de contatos.");
		
		Contato contato = contatoOptional.get();
		EnderecoRecord consultarCep = viaCepService.consultarCep(record.cep());
		
		contato.setNome(record.nome());
		contato.setEmail(record.email());
		contato.setTelefone(record.telefone());
		contato.setCep(record.cep());
		contato.setCidade(consultarCep.localidade());
		contato.setEstado(consultarCep.uf());
		
		return repositorio.save(contato);
	}

	public void deletarContato(String id) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		
		Optional<Contato> contatoOptional = repositorio.findByUsuarioAndId(usuarioAutenticado, id);
		if(contatoOptional.isEmpty())
			throw new IllegalArgumentException("Nenhum contato com o id fornecido foi encontrado na lista de contatos.");
		
		Contato contato = contatoOptional.get();
		repositorio.delete(contato);
	}

}
