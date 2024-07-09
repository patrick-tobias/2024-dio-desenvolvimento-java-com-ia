package br.edu.dio_desenvolvimento_java_com_ia.main;

import br.edu.dio_desenvolvimento_java_com_ia.model.cliente.Cliente;
import br.edu.dio_desenvolvimento_java_com_ia.model.cliente.ClientePessoaFisica;
import br.edu.dio_desenvolvimento_java_com_ia.model.conta.Conta;
import br.edu.dio_desenvolvimento_java_com_ia.model.conta.ContaCorrente;

public class Main {
	public static void main(String[] args) {
		Cliente cliente1 = new ClientePessoaFisica("454.717.490-30", "Cliente 1");
		Conta conta1 = new ContaCorrente(cliente1);
		
		conta1.depositar(1000, "Pagamento Julho/2024");
		conta1.depositar(1000, "loterias");
		
		conta1.sacar(500, "Pagamento contas Água e Luz");
		
		conta1.imprimirExtrato();
		System.out.println("Saldo Final: " + conta1.getSaldo());
		
		
		Cliente cliente2 = new ClientePessoaFisica("391.139.430-65", "Cliente 2");
		Conta conta2 = new ContaCorrente(cliente2);
		
		//conta1.transferir(10000, conta2, "tentativa de transferencia de R$10.000,00");
		//conta2.sacar(10000, "tentativa de saque de R$10.000,00");
		
		conta1.transferir(1000, conta2, "transferência de R$1.000,00 de conta1 para conta2");
		conta2.imprimirExtrato();
		System.out.println("Saldo Final Conta 2: " + conta2.getSaldo());
		
		System.out.println("Saldo Final Conta 1: " + conta1.getSaldo());
	}
}
