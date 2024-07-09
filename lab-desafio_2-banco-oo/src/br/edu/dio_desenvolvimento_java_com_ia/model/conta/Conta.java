package br.edu.dio_desenvolvimento_java_com_ia.model.conta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.dio_desenvolvimento_java_com_ia.model.cliente.Cliente;
import br.edu.dio_desenvolvimento_java_com_ia.model.conta.Politica.IValidacaoSaldo;
import br.edu.dio_desenvolvimento_java_com_ia.model.seguranca.excecao.RegraNegocioException;
import br.edu.dio_desenvolvimento_java_com_ia.model.transacao.Transacao;

public abstract class Conta implements IConta {
	
	private static final String AGENCIA_PADRAO = "1";
	private static int SEQUENCIAL = 1;

	protected String agencia;
	protected String numero;
	private double saldo;
	protected Cliente cliente;
	
	/*
	 * IMPORTANTE: qualquer mudança em transacoes deve ser feita com addTransacao
	 * e deve ser invariante para garantir que o saldo bate com a soma das transações.
	 */
    private final Set<Transacao> transacoes = new HashSet<>();
    private IValidacaoSaldo politicaValidacaoSaldo;

	public Conta(Cliente cliente, IValidacaoSaldo politicaValidacaoSaldo) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.numero = String.valueOf(SEQUENCIAL++);
		this.politicaValidacaoSaldo = politicaValidacaoSaldo;
		this.cliente = cliente;
	}

	@Override
	public void sacar(double valor, String descricao) {
		if(valor <= 0)
			throw new IllegalArgumentException("Valor de saque deve ser positivo.");
		addTransacao(new Transacao(-valor, descricao));
	}
	
	@Override
	public void depositar(double valor, String descricao) {
		if(valor <= 0)
			throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
		addTransacao(new Transacao(valor, descricao));
	}
	
	@Override
	public void transferir(double valor, IConta contaDestino, String descricao) {
		
		/*
		 * IMPORTANTE! Essa deve ser uma operação atômica.
		 */
		this.sacar(valor, descricao);
		contaDestino.depositar(valor, descricao);
	}
	
	public String getAgencia() {
		return agencia;
	}

	public String getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes);
    }
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	private void addTransacao(Transacao transacao) {
		garantirPoliticaDeValidacaoSaldoExistente();
		
		double novoSaldo = saldo + transacao.getValor();
		if(politicaValidacaoSaldo.saldoValido(this, novoSaldo)){
			transacoes.add(transacao);
			setSaldo(saldo + transacao.getValor());			
		} else {
			throw new RegraNegocioException("Saldo não pode ser menor que o limite definido para conta.");
		}
    }
	
	public void garantirPoliticaDeValidacaoSaldoExistente() {
		if(this.politicaValidacaoSaldo == null)
			throw new RegraNegocioException("Obrigatório definição de Política de Saldo.");
	}
}