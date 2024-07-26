package edu.dio.java.design_pattern.infra.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoDeExcecao {
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> violacaoBD(SQLIntegrityConstraintViolationException ex){
		System.out.println(ex);
		String mensagem = "Uma restrição do banco de dados foi violada. " + ex.getMessage();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(mensagem);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> argumentoInvalido(IllegalArgumentException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentoInvalido(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity
        		.badRequest()
        		.body(erros
        				.stream()
        				.map(DadosErroValidacao::new)
        				.toList());
    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> excecaoGeral(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro Interno: não foi possível processar a sua requisição.");
	}
	
	private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
	
}

