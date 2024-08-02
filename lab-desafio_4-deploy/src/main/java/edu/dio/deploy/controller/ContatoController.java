package edu.dio.deploy.controller;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.dio.deploy.model.entity.Contato.Contato;
import edu.dio.deploy.model.entity.Contato.dto.ContatoListarRecord;
import edu.dio.deploy.model.entity.Contato.dto.ContatoRecord;
import edu.dio.deploy.model.service.ContatoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contatos")
@SecurityRequirement(name = "bearer-key")
public class ContatoController {
	
	@Autowired
	private ContatoService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> adicionarContato(@RequestBody @Valid ContatoRecord record, UriComponentsBuilder uriBuilder){
		Contato contato = service.adicionarContato(record);
		URI url = uriBuilder.path("/contatos/{id}").buildAndExpand(contato.getId()).toUri();
		return ResponseEntity.created(url).body(new ContatoListarRecord(contato));
	}
	
	@GetMapping
	public ResponseEntity<?> listarContatos(){
		Collection<ContatoListarRecord> contatos = 
			service.listarContatos()
				.stream()
				.map(ContatoListarRecord::new)
				.collect(Collectors.toList());
		return ResponseEntity.ok(contatos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> visualizarContato(@PathVariable("id") String id){
		Contato contato = service.visualizarContato(id);
		return ResponseEntity.ok(new ContatoListarRecord(contato));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> atualizarContato(@RequestBody @Valid ContatoRecord record){
		Contato contato = service.atualizarContato(record);
		return ResponseEntity.ok(new ContatoListarRecord(contato));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarContato(@PathVariable("id") String id){
		service.deletarContato(id);
		return ResponseEntity.noContent().build();
	}
}
