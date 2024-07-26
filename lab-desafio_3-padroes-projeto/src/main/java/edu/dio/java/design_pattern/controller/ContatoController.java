package edu.dio.java.design_pattern.controller;

import java.net.URI;
import java.util.Collection;

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

import edu.dio.java.design_pattern.model.domain.entity.Contato.Contato;
import edu.dio.java.design_pattern.model.domain.entity.Contato.dto.ContatoListarRecord;
import edu.dio.java.design_pattern.model.domain.entity.Contato.dto.ContatoRecord;
import edu.dio.java.design_pattern.model.service.ContatoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contatos")
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
				.toList();
		return ResponseEntity.ok(contatos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> visualizarContato(@PathVariable String id){
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
	public ResponseEntity<?> deletarContato(@PathVariable String id){
		service.deletarContato(id);
		return ResponseEntity.noContent().build();
	}
}
