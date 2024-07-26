package edu.dio.java.design_pattern.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.dio.java.design_pattern.model.domain.entity.Usuario.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
	
	Optional<Usuario> findByEmail(String email);
	
}
