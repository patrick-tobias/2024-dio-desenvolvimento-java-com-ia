package br.edu.dio_desenvolvimento_java_com_ia.model.conta.Politica;

import br.edu.dio_desenvolvimento_java_com_ia.model.conta.Conta;

public interface IValidacaoSaldo {
	boolean saldoValido(Conta conta, double novoSaldo);
}
