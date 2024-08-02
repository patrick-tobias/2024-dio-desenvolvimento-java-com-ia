package edu.dio.deploy.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.dio.deploy.model.entity.Usuario.Usuario;
import edu.dio.deploy.model.entity.Usuario.dto.UsuarioListarRecord;
import edu.dio.deploy.model.entity.Usuario.dto.UsuarioRecord;
import edu.dio.deploy.model.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;

	@PostMapping
	@Transactional
	public ResponseEntity<?> addUsuario( @RequestBody @Valid UsuarioRecord record, UriComponentsBuilder uriBuilder) {
		Usuario usuario = service.addUsuario(record);
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioListarRecord(usuario));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<?> removerUsuario(@PathVariable("id") String id){
		service.removerUsuario(id);
		return ResponseEntity.noContent().build();
	}
}
