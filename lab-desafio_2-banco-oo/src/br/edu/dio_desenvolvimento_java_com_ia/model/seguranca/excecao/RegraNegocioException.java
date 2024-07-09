package br.edu.dio_desenvolvimento_java_com_ia.model.seguranca.excecao;

public class RegraNegocioException extends RuntimeException {
	public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
