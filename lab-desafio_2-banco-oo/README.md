## Implementação
No desenvolvimento do projeto inicial, foi adicionado:

* **Classe Transação**
    
    A classe ```Transacao``` representa uma transação financeira. Uma transação pode ser um depósito, um saque (retirada) ou uma transferência.

    Principais Atributos:

    * ```dataHora```: Data e Hora da transação.
    * ```valor```: Valor da transação.
    * ```descricao```: Descrição da transação.

    Construtor:

    ```public Transacao(double valor, String descricao)```

    **Observe:** ao criar uma transação, é necessário informar seu valor (positivo para depósito e negativo para retiradas). O atributo de data e hora é instanciado no momento em que se instancia uma nova Transação.

* **Lista de Transações em Conta**

    Encapsulamento do saldo de uma conta, que não é mais um atributo modificável diretamente. Em vez disso, ele é alterado através do método que adiciona transações associadas à conta. Isso garante que o saldo esteja sempre correto e reflete todas as operações realizadas.

    ```private final Set<Transacao> transacoes = new HashSet<>();```
    ```
    public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes);
    }
    ```

    **Observe**: o método acessor de Transações ```getTransacoes()``` retorna uma nova lista do conjunto de transações, reforçando a importância de mantermos somente operações adicionadas através do método de adicionar transação.

* **Política de Saldo**
    
    Foi adicionado a interface ```IValidacaoSaldo```, cuja implementação proporciona a validação do saldo de uma conta, qualquer que seja a política aplicada à ela. Para fins desse estudo, uma conta corrente tem limite de conta fixo -500,00.

* **Classe Cliente**

    Foi adicionada a classe abstrata ```Cliente``` e sua classe filha ```ClientePessoaFísica```. A Classe Cliente agora compõe a classe ```Conta```.


## Exemplo de uso
Aqui está um exemplo básico de como utilizar as classes ```Conta```, ```Cliente``` e ```Transacao```. Execute o método main a seguir:

```
import java.time.LocalDate;

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

```

A saída esperada da execução do método main é:
```
=== Extrato Conta Corrente ===
=== Cliente 1 2024-07-08 ===
Transacao [valor=-500.0, dataHora=2024-07-08T19:13:09.188688, descricao=Pagamento contas Água e Luz]
Transacao [valor=1000.0, dataHora=2024-07-08T19:13:09.188665, descricao=loterias]
Transacao [valor=1000.0, dataHora=2024-07-08T19:13:09.188494, descricao=Pagamento Julho/2024]
=== FIM ===
Saldo Final: 1500.0
=== Extrato Conta Corrente ===
=== Cliente 2 2024-07-08 ===
Transacao [valor=1000.0, dataHora=2024-07-08T19:13:09.196296, descricao=transferência de R$1.000,00 de conta1 para conta2]
=== FIM ===
Saldo Final Conta 2: 1000.0
Saldo Final Conta 1: 500.0
```

## Criando um Banco Digital com Java e Orientação a Objetos

Encontre o desafio no endereço:

```bash
https://github.com/falvojr/lab-banco-digital-oo
```

#### 02/08/2021 - [Mentoria #1: Tire Suas Dúvidas Sobre Orientação a Objetos](https://www.youtube.com/watch?v=YS6ouOhkyNI)

Desafio: Considerando nosso conhecimento no domínio bancário, iremos abstrair uma solução Orientada a Objetos em Java. Para isso, vamos interpretar o seguinte cenário:
“Um banco oferece aos seus clientes dois tipos de contas (corrente e poupança), as quais possuem as funcionalidades de depósito, saque e transferência (entre contas da própria instituição).”

#### Abstração
Habilidade de concentrar-se nos aspectos essenciais de um domínio, ignorando características menos importantes ou acidentais. Nesse contexto, objetos são abstrações de entidades existentes no domínio em questão.

#### Encapsulamento
Encapsular significa esconder a implementação dos objetos, criando assim interfaces de uso mais concisas e fáceis de usar/entender. O encapsulamento favorece principalmente dois aspectos de um sistema: a manutenção e a evolução.

#### Herança
Permite que você defina uma classe filha que reutiliza (herda), estende ou modifica o comportamento de uma classe pai. A classe cujos membros são herdados é chamada de classe base. A classe que herda os membros da classe base é chamada de classe derivada.

#### Polimorfismo
Capacidade de um objeto poder ser referenciado de várias formas, ou seja, é capacidade de tratar objetos criados a partir das classes específicas como objetos de uma classe genérica. Cuidado, polimorfismo não quer dizer que o objeto fica se transformando, muito pelo contrário, um objeto nasce de um tipo e morre daquele tipo, o que pode mudar é a maneira como nos referimos a ele.