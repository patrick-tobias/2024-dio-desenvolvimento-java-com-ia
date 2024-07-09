package br.edu.dio_desenvolvimento_java_com_ia.model.transacao;

import java.time.LocalDateTime;

import br.edu.dio_desenvolvimento_java_com_ia.model.seguranca.excecao.RegraNegocioException;

public class Transacao {
    private double valor;
    private LocalDateTime dataHora;
    private String descricao;

    public Transacao(double valor, String descricao) {
        setValor(valor);
        setDataHora(LocalDateTime.now());
        setDescricao(descricao);
    }
    
    public double getValor() {
        return valor;
    }

    private void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    private void setDataHora(LocalDateTime dataHora) {
        if(dataHora == null) {
            throw new RegraNegocioException("Data e hora da transação é obrigatória.");
        }
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    private void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	@Override
	public String toString() {
		return "Transacao [valor=" + valor + ", dataHora=" + dataHora + ", descricao=" + descricao + "]";
	}
    
    
}
