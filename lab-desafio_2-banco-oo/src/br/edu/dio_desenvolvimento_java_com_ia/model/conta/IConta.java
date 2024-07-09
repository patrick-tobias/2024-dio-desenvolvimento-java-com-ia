package br.edu.dio_desenvolvimento_java_com_ia.model.conta;


public interface IConta {
	void sacar(double valor, String descricao);
	void depositar(double valor, String descricao);
	void transferir(double valor, IConta contaDestino, String descricao);
	void imprimirExtrato();
}