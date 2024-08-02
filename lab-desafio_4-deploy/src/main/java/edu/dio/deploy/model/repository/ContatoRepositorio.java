package edu.dio.deploy.model.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.dio.deploy.model.entity.Contato.Contato;
import edu.dio.deploy.model.entity.Usuario.Usuario;

@Repository
public interface ContatoRepositorio extends JpaRepository<Contato, String>{

	Collection<Contato> findAllByUsuario(Usuario usuarioAutenticado);
	Optional<Contato> findByUsuarioAndId(Usuario usuarioAutenticado, String id);
	void deleteAllByUsuario(Usuario usuarioAutenticado);
}
