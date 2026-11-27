// sem linha "package"

import Cadastro.Animal;
import Cadastro.CadastroService;
import Cadastro.Proprietario;
import Cadastro.RegistroClinico;

import agendamentos.Agendamento;
import agendamentos.AgendamentoCirurgia;
import agendamentos.AgendamentoConsulta;

import exames.Exame;
import exames.ExameService;

import pagamentos.Cobravel;
import pagamentos.Pagador;
import pagamentos.Pagamento;
import pagamentos.ServicoPagamento;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CadastroConsole {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final CadastroService cadastro;
    private final ExameService exameService;
    private final Scanner entrada = new Scanner(System.in);

    /** Agendamentos (consultas e cirurgias) mantidos em memória durante a execução. */
    private final List<Agendamento> agendamentos = new ArrayList<>();

    public CadastroConsole(CadastroService cadastro, ExameService exameService) {
        this.cadastro = cadastro;
        this.exameService = exameService;
    }

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
        System.out.println("9 - Agendar consulta");
        System.out.println("10 - Agendar cirurgia");
        System.out.println("11 - Listar agendamentos");
        System.out.println("12 - Cancelar agendamento");
        System.out.println("13 - Cobrar Serviço");
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
            case "9" -> agendarConsulta();
            case "10" -> agendarCirurgia();
            case "11" -> listarAgendamentos();
            case "12" -> cancelarAgendamento();
            case "13" -> cobrarServico();
            default -> System.out.println("Opção inválida.");
        }
    }

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
    // Agendamentos (consulta e cirurgia)
    // ------------------------------------------------------------------

    private void agendarConsulta() {
        Animal animal = escolherAnimal();
        boolean vip = donoEhVip(animal);
        String veterinarioId = ler("Veterinário (identificação): ");
        LocalDateTime inicio = lerDataHora("Data e hora (dd/MM/aaaa HH:mm): ");
        String prontuario = ler("Prontuário/observações (opcional): ");

        Agendamento novo = new AgendamentoConsulta(animal, veterinarioId, vip, inicio, prontuario);
        registrarAgendamento(novo);
    }

    private void agendarCirurgia() {
        Animal animal = escolherAnimal();
        boolean vip = donoEhVip(animal);
        String veterinarioId = ler("Veterinário (identificação): ");
        LocalDateTime inicio = lerDataHora("Data e hora (dd/MM/aaaa HH:mm): ");
        String prontuario = ler("Prontuário/observações (opcional): ");
        BigDecimal valor = lerValor("Valor da cirurgia (R$): ");

        Agendamento novo = new AgendamentoCirurgia(animal, veterinarioId, vip, inicio, prontuario, valor);
        registrarAgendamento(novo);
    }

    /** Verifica conflito na agenda do veterinário e, se estiver livre, guarda o agendamento. */
    private void registrarAgendamento(Agendamento novo) {
        for (Agendamento existente : agendamentos) {
            if (novo.conflitaCom(existente)) {
                throw new IllegalStateException("Conflito de horário com: " + existente);
            }
        }
        agendamentos.add(novo);
        System.out.println("Agendamento realizado: " + novo);
    }

    /** Lista os agendamentos por prioridade (VIP primeiro) e depois por horário. */
    private void listarAgendamentos() {
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }
        List<Agendamento> ordenados = new ArrayList<>(agendamentos);
        Collections.sort(ordenados);
        for (int i = 0; i < ordenados.size(); i++) {
            Agendamento a = ordenados.get(i);
            System.out.println((i + 1) + " - " + a + " | vet: " + a.getVeterinarioId());
        }
    }

    private void cancelarAgendamento() {
        if (agendamentos.isEmpty()) {
            throw new IllegalStateException("Não há agendamentos para cancelar.");
        }
        List<Agendamento> ordenados = new ArrayList<>(agendamentos);
        Collections.sort(ordenados);
        for (int i = 0; i < ordenados.size(); i++) {
            System.out.println((i + 1) + " - " + ordenados.get(i));
        }
        Agendamento escolhido = ordenados.get(lerIndice("Agendamento: ", ordenados.size()));
        agendamentos.remove(escolhido);
        System.out.println("Agendamento cancelado.");
    }

    private boolean donoEhVip(Animal animal) {
        return cadastro.buscarProprietario(animal.getProprietarioId()).isVip();
    }

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

    private LocalDateTime lerDataHora(String rotulo) {
        String texto = ler(rotulo);
        try {
            return LocalDateTime.parse(texto.trim(), FORMATO_DATA_HORA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data/hora inválida. Use o formato dd/MM/aaaa HH:mm.");
        }
    }

    private BigDecimal lerValor(String rotulo) {
        String texto = ler(rotulo).trim().replace(",", ".");
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido. Exemplo: 850,00");
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

    private void cobrarServico(){
        Proprietario dono = escolherProprietario();

        System.out.println("1 - Consulta (R$ 120,00)");
        System.out.println("2 - Vacina (R$ 80,00)");
        System.out.println("3 - Exame (60,00)");

        int opcao = lerIndice("Servico:", 3);
        String tipo;
        double valor;

        switch (opcao){
            case -> 0 {tipo = "Consulta"; valor = 120.00;}
            case -> 1 {tipo = "Vacina"; valor = 80.00;}
            default -> {tipo = "Exame"; valor = 60.00;}
        }

        Pagador pagador = new Pagador(){
            public String getNome(){ return nome; }
            public double getIsVip(){ return dono.isVip; }
            public String pagamentos.Assinatura getAssinaturas(){ return null; }
        };

        Cobravel servico = new Cobravel(){
            public String getTipo(){ return tipo; }
            public double getValaor(){ return valor; }
            public String getDescricao(){ return descricao; }
        };

        Pagamento pagamento = ServicoPagamento.cobrar(pagador, List.of(servico));
        System.out.println(pagamento);

        if (lerSimNao("Confirmar pagamento? (s/n):")){
           pagamento.pagar();
           System.out.println("Pagamento confirmado.");
        }
        
    }
}
