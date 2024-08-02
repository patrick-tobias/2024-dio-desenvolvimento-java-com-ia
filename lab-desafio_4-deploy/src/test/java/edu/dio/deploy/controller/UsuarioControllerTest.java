package edu.dio.deploy.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.dio.deploy.model.entity.Usuario.dto.UsuarioListarRecord;
import edu.dio.deploy.model.entity.Usuario.dto.UsuarioRecord;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class UsuarioControllerTest {
	
	private static final String API = "/usuarios";
	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	@DisplayName("Deve retornar código Http 201 created, quando usuário cadastrado.")
	public void cadastrar_c1() throws Exception {
		UsuarioRecord usuarioRecord = getUsuarioRecord();
		UsuarioListarRecord usuarioListarRecord = getUsuarioListarRecord();
		
		String json = new ObjectMapper().writeValueAsString(usuarioRecord);
		String jsonResponse = new ObjectMapper().writeValueAsString(usuarioListarRecord);
		
		MockHttpServletRequestBuilder request = 
			MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);
		
		MockHttpServletResponse response = 
			mvc
				.perform(request)
				.andReturn()
				.getResponse();
		
		Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonResponse);
	}
	
	@Test
	@DisplayName("Deve retornar código Http 400 bad request, quando usuário record está inválido.")
	public void cadastrar_c2() throws Exception {
		UsuarioRecord usuarioRecord = new UsuarioRecord("", "", "");
		String json = new ObjectMapper().writeValueAsString(usuarioRecord);
		
		MockHttpServletRequestBuilder request = 
			MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);
		
		MockHttpServletResponse response = 
			mvc
				.perform(request)
				.andReturn()
				.getResponse();
		
		Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deve retornar código Http 400 bad request, quando requisição com corpo vazio")
	public void cadastrar_c3() throws Exception {
		MockHttpServletRequestBuilder request = 
			MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content("");
		
		MockHttpServletResponse response = 
			mvc
				.perform(request)
				.andReturn()
				.getResponse();
		
		Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		Assertions.assertThat(response.getContentAsString()).isEqualTo("A requisição está incorreta ou o corpo está vazio. Verifique os dados enviados e tente novamente.");
	}
	
	private UsuarioRecord getUsuarioRecord() {
		return new UsuarioRecord("João da Silva", "joaodasilva@email.com", "@Senha1234");
	}
	
	private UsuarioListarRecord getUsuarioListarRecord() {
		return new UsuarioListarRecord("João da Silva", "joaodasilva@email.com");
	}
}
