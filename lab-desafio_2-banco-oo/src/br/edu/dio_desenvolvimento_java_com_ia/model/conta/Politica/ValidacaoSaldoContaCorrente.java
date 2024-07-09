package br.edu.dio_desenvolvimento_java_com_ia.model.conta.Politica;

import br.edu.dio_desenvolvimento_java_com_ia.model.conta.Conta;

public class ValidacaoSaldoContaCorrente implements IValidacaoSaldo{
	
	private static final double LIMITE_CONTA_CORRENTE = -500d;

	@Override
	public boolean saldoValido(Conta conta, double novoSaldo) {
		return novoSaldo >= LIMITE_CONTA_CORRENTE;
	}

}
