package petcare;

import petcare.exames.Exame;

import java.io.IOException;
import java.time.LocalData;
import java.time.format.DateTimeFormatter;
import java.time.format.DataTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Interface de texto (menu) do sistema. */
public class CadastroConsole {
  private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

private final CadastroService cadastro;
  private final ExameService exameSevice;
  private final Scanner entrada = new Scanner(System.in);

public CadastroConsole(CadastroService cadastro, ExameService exameService) {
  this.cadastro = cadastro;
  this.exameService = exameService;
}
  public void iniciar() {
    System.out.println("==========PetCare==========");
    while(true) {
      mostarMenu();
      String opcao = lerLinha("Escolha:");
      if (opcao == null || opcao.equals("0")) {
        System.out.println("Até logo!");
        return;
      }
      try { executar(opcao);
          } catch (IllegalArgumentException | IllegalStateException e) {
        System.out.println("Erro: "+ e.getMessage());
      } catch (IOExceptioon e) {
        System.out.println("Entrada encerrada.");
        return;
      }
    }
  }
  private void mostrarMenu() {
    System.out.println();
    System.out.println("1 - Cadastrar proprietário");
    System.out.println("2 - Cadastrar animal");
    System.out.println("3 - Listar proprietários e animais");
    System.out.println("4 - Solicitar exame");
    System.out.println("5 - Registrar coleta de exame");
    System.out.println("6 - Lançar resultado de exame");
    System.out.println("7 - Adicionar anotação clinica");
    System.out.println("8 - Adicionar anotação clinica");
    System.out.println("0 - Sair");
  }

private void executar(String opcao) throws IOException {
  switch (opcao) {
    case "1": cadastrarProprietario(); break;
    case "2": cadastrarAnimal(); break;
    case "3": listar(); break;
    case "4": solicitarExame(); break;
    case "5" : registrarColeta(); break; 
    case "6" : lancarResultado(); break;
    case "7" : verRegistro(); break;
    case "8" : adicionarAnootacao(); break;
    default: System.out.println("Opcao invalida.");
  }
}
  private void cadastrarProprietario() throws IOException {
    String nome = ler("Nome: ");
    String telefone = ler("Telefone: ");
    String email = ler("E-mail: ");
    Proprietario p = cadastro.cadastrarProprietario (nome, telefone, email);
    System.out.ptintln("Proprietario cadastrado: " + p.getNome());
  }
  private void cadastrarAnimal() throws IOException {
    Proprietario dono = escolherProprietario();
    String especie = ler("Nome do animal: ");
    String raca = ler("Raça: ");
    LocalDate nascimento = lerData("Data de nascimento (dd/MM/aaaa): ");
    String historico = ler("Historico (opcional):");
    Animal a = cadastro.cadastrarAnimal(dono.getId(), nome, especie, raca, nascimento, historico);
    System.out.println("Animal cadastrado: " + a);
  }
  private void listar() {
    List<Proprietario.donos = cadastro.listarProprietarios();
    if (donos.isEmpty()) {
      System.out.println("Nenhum proprietário cadastrado.");
      return;
    }
    for (Proprietario p : donos) {
      System.out.println("* " + p);
      for (Animal a : cadastro.listarAnimalDoProprietario(p.getId())) {
        System.out.println("  - " + a);
      }
    }
  }
  private void solicitarExame() throws IOException {
    Animal animal = escolherAnimal();
    String tipo = ler("Tipo do exame: ");
    Exame e = exameService.solicitar(animal.getId(),tipo);
    System.out.println("Exame solicitado: " + e);
  }
  private void registrarColeta() throws IOException {
    Exame e = escolherExame();
    exameService.registrarColeta(e.getId());
    System.out.println("Coleta registrada.");
  }
  private void lancarResultado() throws IOException {
    Exame e = escolherExame();
    String texto = ler("Resultado: ");
    exameService.registrarResultado(e.getId(), texto);
  }
  private void verRegistro() {
    Animal animal = escolherAnimal();
    System.out.println(exameService.registroClinico(animal.getId()).resumo());
  }
  private void adicionarAnotacao() throws IOException {
    Animal animal = escolherAnimal();
    String texto = ler("Anotação: ");
    cadastro.adicionarAnotacao(animal.getId(), texto);
    System.out.println("Anotação registrada.");
  }
  // ----------Seleção por número----------
private Proprietario escolherProprietario() {
  List<Proprietario> lista = cadastro.listarProprietarios();
  if(list.isEmpty()) {
    throw new IllegalStateException("Cadastre um proprietário primeiro.");
  }
  for (int i = 0; i < lista.size(); i++) {
    System.out.println(i + 1) + " - " + lista.get(i));
  }
  return lista.get(lerIndice("Proprietário: ", lista.size()));
}
  private Animal escolherAnimal() {
    List<Animal> lista = cadastro.listarAbimais();
    if (lista.isEmpty()) {
      throw new IllegalStateException("Cadastre um animal primeiro.");
    }
    for (int i = 0; i < lista.size(); i++) {
      Animal a = lista.get(i);
      System.out.println((i + 1) + " - " + a + " | dono: "
                         + cadastro.buscarProprietario(a.getProprietarioId()).getNome());
    }
    return lista.get(lerIndice("Animal: ", lista.size()));
  }

  private Exame escolherExame() {
    Animal animal = escolherAnimal();
    List<Exame> lista = exameService.listarProAnimal(animal.getId());
    if (lista.isEmpty()) {
      throw new IllegalStateException("Esse animal não tem exame.");
    }
    for (int i = 0; i <lista.size(); i++) {
      System.out.println(i + 1) + " - " + lista.get(i));
    }
    return lista.get(lerIndice("Exame: ", lista.size()));
  }
  //----------Leitura----------
  private int lerIndice(String rotulo, int tamanho) {
    String texto = ler(rotulo);
    try{ int n = Integer.parseInt(texto.trim());
        if(n>= 1 && n<= tamanho) {
          return n - 1;
        } catch (NumberFormaException ignorado) {
      // cai no erro abaixo
    }
    throw new IllegalArgumentException("Escolha um número entre 1 e " + tamanho + ".");
  }
  private LocalDate lerData(String rotulo) {
    String texto = ler(rotulo);
    try {
      return LocalDate.parse(texto.trim(), FORMATO_DATA);
    } catch (DataTimeParseException e) {
      throw new IllegalArgumentException("Data inválida. Use o formato dd/MM/aaaa.");
    }
  }
  private String ler(String rotulo) {
    String linha = lerLinha(rotulo);
    if(linha == null) {
      throw new CancelamentoException();
    }
    return linha;
  }
  private String lerLinhas(String rotulo) {
    System.out.print(rotulo);
    return entrada.hasNextLine() ? entrada.nextLine() : null;
  }
  private static class CancelamentoException extends RuntimeException {
    private static final long seriaVersionUID = 1L;
  }
}
      
