package edu.dio.deploy.model.service.implementacao;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.dio.deploy.infra.exception.RegraNegocioException;
import edu.dio.deploy.model.adapter.UserDetailsParaUsuarioAdapter;
import edu.dio.deploy.model.entity.Contato.Contato;
import edu.dio.deploy.model.entity.Contato.dto.ContatoRecord;
import edu.dio.deploy.model.entity.Contato.dto.EnderecoRecord;
import edu.dio.deploy.model.entity.Usuario.Usuario;
import edu.dio.deploy.model.repository.ContatoRepositorio;
import edu.dio.deploy.model.service.ContatoService;
import edu.dio.deploy.model.service.ViaCepService;

@Service
public class ContatoServiceImplementacao implements ContatoService {

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
	
	@Override
	public Contato adicionarContato(ContatoRecord record) {
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
		
		if(listarContatos().contains(contato))
			throw new RegraNegocioException("Contato já cadastrado na sua lista de contatos.");
		
		return repositorio.save(contato);
	}
	
	@Override
	public Collection<Contato> listarContatos() {
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		return repositorio.findAllByUsuario(usuarioAutenticado);
	}

	@Override
	public Contato visualizarContato(String id) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();

		Optional<Contato> contatoOptional = repositorio.findByUsuarioAndId(usuarioAutenticado, id);
		if(contatoOptional.isEmpty())
			throw new IllegalArgumentException("Nenhum contato com o id fornecido foi encontrado na lista de contatos.");
		
		return contatoOptional.get();
	}

	@Override
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

	@Override
	public void deletarContato(String id) {
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		
		Optional<Contato> contatoOptional = repositorio.findByUsuarioAndId(usuarioAutenticado, id);
		if(contatoOptional.isEmpty())
			throw new IllegalArgumentException("Nenhum contato com o id fornecido foi encontrado na lista de contatos.");
		
		Contato contato = contatoOptional.get();
		repositorio.delete(contato);
	}

}
