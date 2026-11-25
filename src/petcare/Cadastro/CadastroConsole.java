package Cadastro;

import exames.Exame;
import exames.ExameService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Interface de texto (menu) do sistema. */
public class CadastroConsole {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CadastroService cadastro;
    private final ExameService exameService;
    private final Scanner entrada = new Scanner(System.in);

    public CadastroConsole(CadastroService cadastro, ExameService exameService) {
        this.cadastro = cadastro;
        this.exameService = exameService;
    }

    // ------------------------------------------------------------------
    // Laço principal
    // ------------------------------------------------------------------

    public void iniciar() {
        System.out.println("========== PetCare ==========");
        while (true) {
            mostrarMenu();
            String opcao = lerLinha("Escolha: ");
            if (opcao == null || opcao.trim().equals("0")) {
                System.out.println("Até logo!");
                return;
            }
            try {
                executar(opcao.trim());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro ao acessar os arquivos: " + e.getMessage());
            } catch (CancelamentoException e) {
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
        System.out.println("7 - Ver registro clínico");
        System.out.println("8 - Adicionar anotação clínica");
        System.out.println("0 - Sair");
    }

    private void executar(String opcao) throws IOException {
        switch (opcao) {
            case "1" -> cadastrarProprietario();
            case "2" -> cadastrarAnimal();
            case "3" -> listar();
            case "4" -> solicitarExame();
            case "5" -> registrarColeta();
            case "6" -> lancarResultado();
            case "7" -> verRegistro();
            case "8" -> adicionarAnotacao();
            default -> System.out.println("Opção inválida.");
        }
    }

    // ------------------------------------------------------------------
    // Ações do menu
    // ------------------------------------------------------------------

    private void cadastrarProprietario() throws IOException {
        String nome = ler("Nome: ");
        String telefone = ler("Telefone: ");
        String email = ler("E-mail: ");
        boolean vip = lerSimNao("Cliente VIP? (s/n): ");

        Proprietario p = cadastro.cadastrarProprietario(nome, telefone, email, vip);
        System.out.println("Proprietário cadastrado: " + p.getNome());
    }

    private void cadastrarAnimal() throws IOException {
        Proprietario dono = escolherProprietario();
        String nome = ler("Nome do animal: ");
        String especie = ler("Espécie: ");
        String raca = ler("Raça: ");
        LocalDate nascimento = lerData("Data de nascimento (dd/MM/aaaa): ");
        String historico = ler("Histórico (opcional): ");

        Animal a = cadastro.cadastrarAnimal(dono.getId(), nome, especie, raca, nascimento, historico);
        System.out.println("Animal cadastrado: " + a);
    }

    private void listar() {
        List<Proprietario> donos = cadastro.listarProprietarios();
        if (donos.isEmpty()) {
            System.out.println("Nenhum proprietário cadastrado.");
            return;
        }
        for (Proprietario p : donos) {
            System.out.println("* " + p);
            for (Animal a : cadastro.listarAnimaisDoProprietario(p.getId())) {
                System.out.println("  - " + a);
            }
        }
    }

    private void solicitarExame() throws IOException {
        Animal animal = escolherAnimal();
        String tipo = ler("Tipo do exame: ");
        Exame exame = exameService.solicitar(animal.getId(), tipo);
        System.out.println("Exame solicitado: " + exame);
    }

    private void registrarColeta() throws IOException {
        Exame exame = escolherExame();
        exameService.registrarColeta(exame.getId());
        System.out.println("Coleta registrada.");
    }

    private void lancarResultado() throws IOException {
        Animal animal = escolherAnimal();
        Exame exame = escolherExame(animal);
        String texto = ler("Resultado: ");
        String nomeDono = cadastro.buscarProprietario(animal.getProprietarioId()).getNome();
        exameService.lancarResultado(exame.getId(), texto, nomeDono);
        System.out.println("Resultado registrado.");
    }

    /** Monta o registro clínico usando a classe RegistroClinico e exibe o resumo. */
    private void verRegistro() throws IOException {
        Animal animal = escolherAnimal();
        Proprietario dono = cadastro.buscarProprietario(animal.getProprietarioId());
        List<Exame> exames = exameService.listarPorAnimal(animal.getId());
        List<String> notas = cadastro.listarAnotacoes(animal.getId());

        RegistroClinico registro = new RegistroClinico(animal, dono, exames, notas);

        System.out.println();
        System.out.println(registro.resumo());
    }

    private void adicionarAnotacao() throws IOException {
        Animal animal = escolherAnimal();
        String texto = ler("Anotação: ");
        cadastro.adicionarAnotacao(animal.getId(), texto);
        System.out.println("Anotação registrada.");
    }

    // ------------------------------------------------------------------
    // Seleção por número
    // ------------------------------------------------------------------

    private Proprietario escolherProprietario() {
        List<Proprietario> lista = cadastro.listarProprietarios();
        if (lista.isEmpty()) {
            throw new IllegalStateException("Cadastre um proprietário primeiro.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i));
        }
        return lista.get(lerIndice("Proprietário: ", lista.size()));
    }

    private Animal escolherAnimal() {
        List<Animal> lista = cadastro.listarAnimais();
        if (lista.isEmpty()) {
            throw new IllegalStateException("Cadastre um animal primeiro.");
        }
        for (int i = 0; i < lista.size(); i++) {
            Animal a = lista.get(i);
            String dono = cadastro.buscarProprietario(a.getProprietarioId()).getNome();
            System.out.println((i + 1) + " - " + a + " | dono: " + dono);
        }
        return lista.get(lerIndice("Animal: ", lista.size()));
    }

    private Exame escolherExame() {
        return escolherExame(escolherAnimal());
    }

    private Exame escolherExame(Animal animal) {
        List<Exame> lista = exameService.listarPorAnimal(animal.getId());
        if (lista.isEmpty()) {
            throw new IllegalStateException("Esse animal não tem exames.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i));
        }
        return lista.get(lerIndice("Exame: ", lista.size()));
    }

    // ------------------------------------------------------------------
    // Leitura de entrada
    // ------------------------------------------------------------------

    private int lerIndice(String rotulo, int tamanho) {
        String texto = ler(rotulo);
        try {
            int n = Integer.parseInt(texto.trim());
            if (n >= 1 && n <= tamanho) {
                return n - 1;
            }
        } catch (NumberFormatException ignorado) {
            // cai no erro abaixo
        }
        throw new IllegalArgumentException("Escolha um número entre 1 e " + tamanho + ".");
    }

    private LocalDate lerData(String rotulo) {
        String texto = ler(rotulo);
        try {
            return LocalDate.parse(texto.trim(), FORMATO_DATA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato dd/MM/aaaa.");
        }
    }

    private boolean lerSimNao(String rotulo) {
        String texto = ler(rotulo).trim().toLowerCase();
        return texto.equals("s") || texto.equals("sim");
    }

    private String ler(String rotulo) {
        String linha = lerLinha(rotulo);
        if (linha == null) {
            throw new CancelamentoException();
        }
        return linha;
    }

    /** Lê uma linha; devolve null se a entrada acabou. */
    private String lerLinha(String rotulo) {
        System.out.print(rotulo);
        return entrada.hasNextLine() ? entrada.nextLine() : null;
    }

    private static class CancelamentoException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}