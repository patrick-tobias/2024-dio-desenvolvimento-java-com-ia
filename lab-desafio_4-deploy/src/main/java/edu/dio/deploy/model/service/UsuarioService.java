package edu.dio.deploy.model.service;

import edu.dio.deploy.model.entity.Usuario.Usuario;
import edu.dio.deploy.model.entity.Usuario.dto.UsuarioRecord;

public interface UsuarioService {
	Usuario addUsuario(UsuarioRecord record);
	void removerUsuario(String id);
}
