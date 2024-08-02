package edu.dio.deploy.infra.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoDeExcecao {
	
	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<?> regraNegocio(RegraNegocioException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
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
        				.collect(Collectors.toList()));
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> corpoIncompleto(HttpMessageNotReadableException ex){
		return ResponseEntity.badRequest().body("A requisição está incorreta ou o corpo está vazio. Verifique os dados enviados e tente novamente.");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> excecaoGeral(Exception ex){
		System.out.println("Erro inesperado: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro Interno: não foi possível processar a sua requisição.");
	}
	
	private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
	
}
