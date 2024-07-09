import br.edu.dio_desenvolvimento_java_com_ia.model.Iphone;
import br.edu.dio_desenvolvimento_java_com_ia.model.internet.NavegadorInternet;
import br.edu.dio_desenvolvimento_java_com_ia.model.musica.ReprodutorMusical;
import br.edu.dio_desenvolvimento_java_com_ia.model.telefone.AparelhoTelefonico;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("2024 DIO Desenvolvimento Java com IA.");
        
        ReprodutorMusical reprodutorMusical = new Iphone();
        reprodutorMusical.tocar();
        reprodutorMusical.pausar();
        reprodutorMusical.selecionarMusica("Raul Seixas - O Carimbador Maluco");
        
        AparelhoTelefonico aparelhoTelefonico  = new Iphone();
        aparelhoTelefonico.ligar("(XX) XXXXX-XXXX");
        aparelhoTelefonico.atender();
        aparelhoTelefonico.iniciarCorreioVoz();
        
        NavegadorInternet navegadorInternet = new Iphone();
        navegadorInternet.exibirPagina("dio.me");
        navegadorInternet.adicionarNovaAba();
        navegadorInternet.atualizarPagina();
    }
}
