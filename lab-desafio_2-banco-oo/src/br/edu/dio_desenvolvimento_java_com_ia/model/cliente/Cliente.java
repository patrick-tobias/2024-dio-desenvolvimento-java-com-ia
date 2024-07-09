package br.edu.dio_desenvolvimento_java_com_ia.model.cliente;

public abstract class Cliente {
	protected String nome;
	
	public Cliente(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
