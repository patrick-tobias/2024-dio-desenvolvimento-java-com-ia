package br.edu.dio_desenvolvimento_java_com_ia.model;

import br.edu.dio_desenvolvimento_java_com_ia.model.internet.NavegadorInternet;
import br.edu.dio_desenvolvimento_java_com_ia.model.musica.ReprodutorMusical;
import br.edu.dio_desenvolvimento_java_com_ia.model.telefone.AparelhoTelefonico;

public class Iphone implements ReprodutorMusical, AparelhoTelefonico, NavegadorInternet{

	@Override
	public void exibirPagina(String url) {
		System.out.println("ATENDER CHAMADA VIA IPHONE.");
	}

	@Override
	public void adicionarNovaAba() {
		System.out.println("ADICIONANDO ABA VIA IPHONE.");
	}

	@Override
	public void atualizarPagina() {
		System.out.println("ATUALIZAR PAGINA VIA IPHONE.");
	}

	@Override
	public void ligar(String numero) {
		System.out.println(String.format("LIGANDO PARA %s VIA IPHONE.", numero));
	}

	@Override
	public void atender() {
		System.out.println("ATENDER CHAMADA VIA IPHONE.");
	}

	@Override
	public void iniciarCorreioVoz() {
		System.out.println("CORREIO DE VOZ INICIADO VIA IPHONE.");
	}

	@Override
	public void tocar() {
		System.out.println("TOCANDO MUSICA VIA IPHONE.");
	}

	@Override
	public void pausar() {
		System.out.println("PAUSANDO MUSICA VIA IPHONE.");
	}

	@Override
	public void selecionarMusica(String musica) {
		System.out.println(String.format("SELECIONAR MUSICA %s VIA IPHONE.", musica));
	}

}
