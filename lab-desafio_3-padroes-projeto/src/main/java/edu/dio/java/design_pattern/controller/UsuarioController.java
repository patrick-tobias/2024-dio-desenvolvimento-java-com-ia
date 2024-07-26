package edu.dio.java.design_pattern.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.dto.UsuarioListarRecord;
import edu.dio.java.design_pattern.model.domain.entity.Usuario.dto.UsuarioRecord;
import edu.dio.java.design_pattern.model.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;

	@PostMapping
	@Transactional
	public ResponseEntity<?> addUsuario( @RequestBody UsuarioRecord record, UriComponentsBuilder uriBuilder) {
		Usuario usuario = service.addUsuario(record);
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioListarRecord(usuario));
	}
}
