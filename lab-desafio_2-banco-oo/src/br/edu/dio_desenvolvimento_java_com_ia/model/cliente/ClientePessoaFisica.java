package br.edu.dio_desenvolvimento_java_com_ia.model.cliente;

public class ClientePessoaFisica extends Cliente {
	private String cpf;
	
	public ClientePessoaFisica(String cpf, String nome) {
		super(nome);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
}
