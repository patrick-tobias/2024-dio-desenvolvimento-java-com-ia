package edu.dio.deploy.model.service.implementacao;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import edu.dio.deploy.infra.exception.RegraNegocioException;
import edu.dio.deploy.model.entity.Usuario.Usuario;
import edu.dio.deploy.model.entity.Usuario.dto.UsuarioRecord;
import edu.dio.deploy.model.repository.UsuarioRepositorio;

@SpringBootTest
@ActiveProfiles("dev")
public class UsuarioServiceImplementacaoTest {
	
	@SpyBean
	private UsuarioServiceImplementacao service;
	
	@MockBean
	private UsuarioRepositorio repositorio;
	
	@SpyBean
	private PasswordEncoder encoder;
	
	@Test
	@DisplayName("Deve lançar RegraNegocioException quando um usuário já existir com o e-mail fornecido.")
	public void addUsuario_c1() {
		UsuarioRecord usuarioRecord = getUsuarioRecord();
		Usuario usuario = getUsuario();
		String email = usuario.getEmail();
		
		Mockito.when(repositorio.findByEmail(email)).thenReturn(Optional.ofNullable(usuario));

		Throwable exception = 
			Assertions.assertThrows(RegraNegocioException.class, () -> service.addUsuario(usuarioRecord));
		
		Assertions.assertEquals("Usuário já cadastrado no sistema.", exception.getMessage());
		
		Mockito.verify(repositorio, Mockito.never()).save(usuario);
	}
	
	@Test
	@DisplayName("Deve lançar RegraNegocioException quando senha não for válida"
			+ "Cenário: senha não contém caractere(s) em maiúsculo.")
	public void addUsuario_c2() {
		UsuarioRecord usuarioRecord = new UsuarioRecord("João da Silva", "joaodasilva@email.com", "@senha1234");
		Usuario usuario = getUsuario();
		String email = usuario.getEmail();
		
		Mockito.when(repositorio.findByEmail(email)).thenReturn(Optional.empty());
		
		Throwable exception = 
				Assertions.assertThrows(IllegalArgumentException.class, () -> service.addUsuario(usuarioRecord));
		
		Assertions.assertEquals("Senha deve conter pelo menos um caractere maiúsculo.", exception.getMessage());
		
		Mockito.verify(repositorio, Mockito.never()).save(usuario);
	}
	
	@Test
	@DisplayName("Deve lançar RegraNegocioException quando senha não for válida"
			+ "Cenário: senha não contém caractere(s) em minúsculo.")
	public void addUsuario_c3() {
		UsuarioRecord usuarioRecord = new UsuarioRecord("João da Silva", "joaodasilva@email.com", "@SENHA1234");
		Usuario usuario = getUsuario();
		String email = usuario.getEmail();
		
		Mockito.when(repositorio.findByEmail(email)).thenReturn(Optional.empty());
		
		Throwable exception = 
				Assertions.assertThrows(IllegalArgumentException.class, () -> service.addUsuario(usuarioRecord));
		
		Assertions.assertEquals("Senha deve conter pelo menos um caractere minúsculo.", exception.getMessage());
		
		Mockito.verify(repositorio, Mockito.never()).save(usuario);
	}
	
	@Test
	@DisplayName("Deve lançar RegraNegocioException quando senha não for válida"
			+ "Cenário: senha não contém tamanho mínimo.")
	public void addUsuario_c4() {
		UsuarioRecord usuarioRecord = new UsuarioRecord("João da Silva", "joaodasilva@email.com", "@Senha");
		Usuario usuario = getUsuario();
		String email = usuario.getEmail();
		
		Mockito.when(repositorio.findByEmail(email)).thenReturn(Optional.empty());
		
		Throwable exception = 
				Assertions.assertThrows(IllegalArgumentException.class, () -> service.addUsuario(usuarioRecord));
		
		Assertions.assertEquals("Senha deve conter pelo menos 8 caracteres.", exception.getMessage());
		
		Mockito.verify(repositorio, Mockito.never()).save(usuario);
	}
	
	@Test
	@DisplayName("Deve salvar usuário quando informações válidas.")
	public void addUsuario_c5() {
		UsuarioRecord usuarioRecord = getUsuarioRecord();
		Usuario usuario = getUsuario();
		
		Mockito.when(repositorio.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(repositorio.save(Mockito.any())).thenReturn(usuario);
		
		Usuario usuarioSalvo = service.addUsuario(usuarioRecord);
		
		Assertions.assertNotNull(usuarioSalvo);
		Assertions.assertEquals(usuarioRecord.nome(), usuarioSalvo.getNome());
		Assertions.assertEquals(usuarioRecord.email(), usuarioSalvo.getEmail());
		Assertions.assertTrue(encoder.matches(usuarioRecord.senha(), usuarioSalvo.getSenha()));
		
		Mockito.verify(repositorio, Mockito.times(1)).save(Mockito.any(Usuario.class));
	}
	
	private UsuarioRecord getUsuarioRecord() {
		return new UsuarioRecord("João da Silva", "joaodasilva@email.com", "@Senha1234");
	}
	
	private Usuario getUsuario() {
		return
			Usuario
				.builder()
				.id("9d3f3fc6-90a9-4e4f-8927-e9f83385c761")
				.nome("João da Silva")
				.email("joaodasilva@email.com")
				.senha(encoder.encode("@Senha1234"))
				.build();
	}
}
