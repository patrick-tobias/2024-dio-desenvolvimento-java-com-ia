package br.edu.dio_desenvolvimento_java_com_ia.model.conta;

import java.time.LocalDate;

import br.edu.dio_desenvolvimento_java_com_ia.model.cliente.Cliente;
import br.edu.dio_desenvolvimento_java_com_ia.model.conta.Politica.IValidacaoSaldo;
import br.edu.dio_desenvolvimento_java_com_ia.model.conta.Politica.ValidacaoSaldoContaCorrente;

public class ContaCorrente extends Conta{
	private static IValidacaoSaldo politicaValidacaoSaldoContaCorrente = new ValidacaoSaldoContaCorrente();

	public ContaCorrente(Cliente cliente) {
		super(cliente, politicaValidacaoSaldoContaCorrente);
	}

	@Override
	public void imprimirExtrato() {
		System.out.println("=== Extrato Conta Corrente ===");
		System.out.println("=== " + super.getCliente().getNome() + " " + LocalDate.now() + " ===");
		super.getTransacoes().forEach(transacao -> System.out.println(transacao));
		System.out.println("=== FIM ===");
	}

}
