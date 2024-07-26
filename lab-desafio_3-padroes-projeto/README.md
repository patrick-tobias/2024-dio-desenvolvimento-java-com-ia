## Implementação
Neste projeto foi desenvolvido um pequeno sistema de Agenda de Contatos, Cadastro de usuários e CRUD de contatos. O objetivo do projeto foi visualizar e compreender padrões de projeto no desenvolvimento com Spring Boot e como aplicá-los.

### Padrões Criacionais

#### Singleton

O Padrão Singleton assegura que uma classe possua somente uma instância e acesso global à ela. No Spring Boot, o padrão Singleton é gerenciado automaticamente pelo *container* de Inversão de Controle (IoC) do Spring. Assim, para adicionar um Singleton, utilizamos as anotações `@Bean` e `@Autowired`.

``` java
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
```

No exemplo, a anotação `@Bean` é utilizada para indicar que o método retornará um objeto que será gerenciado pelo *container* do Spring, que será um Singleton por padrão.

#### Builder

O Padrão Builder permite que seja criado objetos complexos passo a passo, de modo mais flexível e simplificado. No Spring Boot, o padrão pode ser utilizado convenientemente com a biblioteca **Lombok** e a anotação `@Builder`. **Lombok** é uma biblioteca Java que automatiza a geração de códigos. (veja mais da biblioteca [Lombok](https://projectlombok.org/))

``` java
  public Usuario addUsuario(UsuarioRecord record) {
    validarSenha(record.senha());
    String senha = encoder.encode(record.senha());
    
    Usuario usuario =
      Usuario
        .builder()
        .nome(record.nome())
        .email(record.email())
        .senha(senha)
        .build();
    
    return repositorio.save(usuario);
  }
```

No exemplo, um objeto do tipo `Usuario` foi instanciado utilizando o padrão Builder, implementado através da biblioteca **Lombok**. 

``` java
  @Builder
  public class Usuario {
      //lista de atributos
  }
```

Quando utilizamos a anotação `@Builder` em uma classe, o **Lombok** gera uma classe Builder (`UsuarioBuilder`) com métodos de encadeamento para cada campo da classe, um método estático `builder()` que retorná uma instância de Builder (`UsuarioBuilder`) e um método `build()` que retornará uma instância da classe (`Usuario`).

Apesar de simples, o exemplo ilustra a criação de objetos complexos "escondendo" a complexidade da criação, utilizando uma abordagem "passo-a-passo".

### Padrões Comportamentais

#### Strategy

O Padrão Strategy pemite definir uma família de algoritmos, encapsulá-los e torná-los possíveis de variação, independentemente dos clientes que o utilizam. No Spring, o padrão Strategy pode ser implementado com das anotações `@Service` e `@Component`, que nos permitem definir Beans gerenciados pelo Spring e que podem ser injetados quando necessário.

Para exemplificar a aplicação do padrão, adicionei um método de validação de senhas no cadastro de um usuário.

A interface de validação _IPoliticaValidacaoSenha
``` java
  public interface _IPoliticaValidacaoSenha {
    void validar(String senha);
  }
```

Implementação concretas da interface

``` java
  @Component
  public class ExistenciaMaiusculo implements _IPoliticaValidacaoSenha {
  
    /**
    * Validate the password to ensure it contains at least one uppercase character.
    * 
    * @param senha is the password to be validated
    * @throws IllegalArgumentException if the password does not contain at least one uppercase character
    */
    @Override
    public void validar(String senha) {
      if (!Pattern.matches(".*[A-Z].*", senha)) {
        throw new IllegalArgumentException("Senha deve conter pelo menos um caractere maiúsculo.");
      }
    }
  
  }
```

Outras implementações da interface:
- Existencia de Maiúsculo;
- Existencia de Minúsculo;
- Tamanho mínimo;

A Classe de contexto `UsuarioService` utiliza essas estratégias para validar uma senha e recebe uma coleção de implementações de `_IPoliticaValidacaoSenha` via injeção de dependência e as usa para validar.

```java
  @Autowired
  private Collection<_IPoliticaValidacaoSenha> validacoes;
  
  private void validarSenha(String senha) {
    validacoes.forEach( validacao -> validacao.validar(senha));
  }
```

O uso do Spring para gerenciar essas implementações torna a solução ainda mais flexível e fácil de manter.

### Padrões Estruturais

#### Facade

O Padrão Facade prevê uma interface de maior nível que torna simplificada a utilização de uma biblioteca, framework ou um conjunto de classes complexo, escondendo sua complexidade e permitindo a interação simplificada.

No projeto, um CEP é informado e dele é extraído as informações de município e estado. 

``` java
  @Autowired
  private ViaCepService viaCepService;
  
  public Contato adicionarContato(@Valid ContatoRecord record) {
    
    EnderecoRecord consultarCep = viaCepService.consultarCep(record.cep());
    
    //continuação do método
  }
```

``` java
  @FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
  public interface ViaCepService {
  
    @GetMapping("/{cep}/json/")
    EnderecoRecord consultarCep(@PathVariable("cep") String cep);
  }
```

**Classe Facade**: No exemplo, a interface `viaCepService` encapsula a lógica de interação com a API e fornecendo um método simples `consultarCep` para um usuário interagir com um CEP fornecido. Para saber mais sobre a criação de clientes HTTP com Feign e Spring Boot, [acesse](https://spring.io/projects/spring-cloud-openfeign#overview).



#### Adapter

O Padrão Adapter permite que objetos com interfaces incompatíveis possam colaborar. Ele permite que uma interface existente seja adaptada a uma interface esperada, permitindo que essas classes colaborem.

Nesse projeto de estudo, o Spring Security espera um objeto que implemente a interface `UserDetails` no processo de autenticação. Nossa classe de domínio `Usuario` não implementa essa interface e, para fins de aprendizado, não alteramos a classe. Em vez disso, criamos a classe `UserDetailsParaUsuarioAdapter` que implementa essa interface e funcionará como um adaptador.

```bash
  public class UserDetailsParaUsuarioAdapter implements UserDetails {
    private final Usuario usuario;
    
    public UserDetailsParaUsuarioAdapter(Usuario usuario) {
      this.usuario = usuario;
    }
  
    // implementação dos métodos do "contrato" da implementação
    
    public Usuario getUsuario() {
      return this.usuario;
    }
  }
```

No processo de autenticação com Spring Security, a classe de serviço que implementa a interface `UserDetailsService`, retorna um objeto `UserDetails` e, como nossa classe de adaptação implementa essa interface, podemos personalizar esse objeto.

```bash
  @Service
  public class LoginService implements UserDetailsService{
  
    @Autowired
    private UsuarioRepositorio repositorio;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario =
      repositorio
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail fornecido."));
      
      return new UserDetailsParaUsuarioAdapter(usuario);
    }
  }
```

Assim, nossa classe `LoginController`, responsável por receber as requisições de login, pode continuar o fluxo de autenticação com um objeto do tipo `Usuario`, do domínio da aplicação.

``` bash
  @RestController
  @RequestMapping("/login")
  public class LoginController {
    
    @Autowired
    private AuthenticationManager auhenticationManager;
    
    @Autowired
    private TokenJwtService tokenService;
    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRecord login){
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
          new UsernamePasswordAuthenticationToken(login.email(), login.senha());
      
      Authentication autenticado = 
          auhenticationManager.authenticate(usernamePasswordAuthenticationToken);
      
      Usuario usuario = 
          ((UserDetailsParaUsuarioAdapter) autenticado.getPrincipal())
            .getUsuario();
      
      String token = tokenService.gerarToken(usuario);
      
      return ResponseEntity.ok(new TokenJwtRecord(token));
    }
    
  
    public record LoginRecord(
        @NotBlank
        @Email
        String email,
        
        @NotBlank
        String senha
        ) {}
    
    public record TokenJwtRecord(
        String token
        ) {}
    
  }
```

Essa adaptação permitiu que fosse mantida intacta a classe `Usuario` e, ao mesmo tempo, com que os componentes do Spring Security possam usar instâncias de Usuario através da interface `UserDetailsParaUsuarioAdapter`.


## Explorando Padrões de Projetos na Prática com Java

Encontre o desafio no endereço:

```bash
https://github.com/digitalinnovationone/lab-padroes-projeto-spring
```

Repositório com as implementações dos padrões de projeto explorados no Lab "Explorando Padrões de Projetos na Prática com Java". Especificamente, este projeto explorou alguns padrões usando o Spring Framework, são eles:
- Singleton
- Strategy/Repository
- Facade
