package Cadastro;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CadastroService {

    private static final String ARQ_PROPRIETARIOS = "proprietarios.txt";
    private static final String ARQ_ANIMAIS = "animais.txt";
    private static final String ARQ_ANOTACOES = "anotacoes.txt";

    private final ArquivosRepositorio arquivos;
    private final Map<String, Animal> animais = new LinkedHashMap<>();
    private final Map<String, Proprietario> proprietarios = new LinkedHashMap<>();
    private final Map<String, List<String>> anotacoes = new LinkedHashMap<>();

    public CadastroService(ArquivosRepositorio arquivos) throws IOException {
        this.arquivos = Objects.requireNonNull(arquivos, "arquivos");
        carregarDados();
    }

    // ------------------------------------------------------------------
    // Carga inicial
    // ------------------------------------------------------------------

    private void carregarDados() throws IOException {
        for (String[] c : arquivos.carregar(ARQ_PROPRIETARIOS)) {
            boolean vip = c.length > 4 && Boolean.parseBoolean(c[4]);
            Proprietario p = new Proprietario(c[0], c[1], c[2], c[3], vip);
            proprietarios.put(p.getId(), p);
        }

        for (String[] c : arquivos.carregar(ARQ_ANIMAIS)) {
            String historico = c.length > 6 ? c[6] : "";
            Animal a = new Animal(c[0], c[1], c[2], c[3], c[4], LocalDate.parse(c[5]), historico);
            animais.put(a.getId(), a);
        }

        for (String[] c : arquivos.carregar(ARQ_ANOTACOES)) {
            anotacoes.computeIfAbsent(c[0], k -> new ArrayList<>()).add(c[1]);
        }
    }

    // ------------------------------------------------------------------
    // Proprietários
    // ------------------------------------------------------------------

    public Proprietario cadastrarProprietario(String nome, String telefone, String email)
            throws IOException {
        return cadastrarProprietario(nome, telefone, email, false);
    }

    public Proprietario cadastrarProprietario(String nome, String telefone, String email, boolean vip)
            throws IOException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do proprietário é obrigatório.");
        }

        Proprietario p = new Proprietario(
                UUID.randomUUID().toString(),
                nome.trim(),
                telefone == null ? "" : telefone.trim(),
                email == null ? "" : email.trim(),
                vip);

        arquivos.adicionar(ARQ_PROPRIETARIOS,
                p.getId(), p.getNome(), p.getTelefone(), p.getEmail(), Boolean.toString(p.isVip()));
        proprietarios.put(p.getId(), p);
        return p;
    }

    public Proprietario buscarProprietario(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID do proprietário é obrigatório.");
        }
        Proprietario p = proprietarios.get(id);
        if (p == null) {
            throw new IllegalArgumentException("Proprietário não encontrado: " + id);
        }
        return p;
    }

    public List<Proprietario> listarProprietarios() {
        return List.copyOf(proprietarios.values());
    }

    // ------------------------------------------------------------------
    // Animais
    // ------------------------------------------------------------------

    public Animal cadastrarAnimal(String proprietarioId, String nome, String especie, String raca,
                                  LocalDate nascimento, String historico) throws IOException {
        buscarProprietario(proprietarioId);
        validarDadosCadastro(nome, especie, raca, nascimento);

        Animal a = new Animal(
                UUID.randomUUID().toString(),
                proprietarioId.trim(),
                nome.trim(),
                especie.trim(),
                raca.trim(),
                nascimento,
                historico == null ? "" : historico.trim());

        arquivos.adicionar(ARQ_ANIMAIS,
                a.getId(),
                a.getProprietarioId(),
                a.getNome(),
                a.getEspecie(),
                a.getRaca(),
                a.getNascimento().toString(),
                a.getHistorico());
        animais.put(a.getId(), a);
        return a;
    }

    public Animal buscarAnimal(String id) {
        Animal a = animais.get(id);
        if (a == null) {
            throw new IllegalArgumentException("Animal não encontrado: " + id);
        }
        return a;
    }

    public List<Animal> listarAnimais() {
        return List.copyOf(animais.values());
    }

    public List<Animal> listarAnimaisDoProprietario(String proprietarioId) {
        return animais.values().stream()
                .filter(a -> a.getProprietarioId().equals(proprietarioId))
                .toList();
    }

    // ------------------------------------------------------------------
    // Anotações
    // ------------------------------------------------------------------

    public void adicionarAnotacao(String animalId, String texto) throws IOException {
        buscarAnimal(animalId);
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Anotação vazia.");
        }

        String registro = LocalDate.now() + " - " + texto.trim();
        arquivos.adicionar(ARQ_ANOTACOES, animalId, registro);
        anotacoes.computeIfAbsent(animalId, k -> new ArrayList<>()).add(registro);
    }

    public List<String> listarAnotacoes(String animalId) {
        return List.copyOf(anotacoes.getOrDefault(animalId, List.of()));
    }

    // ------------------------------------------------------------------
    // Validação
    // ------------------------------------------------------------------

    private void validarDadosCadastro(String nome, String especie, String raca, LocalDate nascimento) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do animal é obrigatório.");
        }
        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("A espécie é obrigatória.");
        }
        if (raca == null || raca.isBlank()) {
            throw new IllegalArgumentException("A raça é obrigatória.");
        }
        if (nascimento == null) {
            throw new IllegalArgumentException("A data de nascimento é obrigatória.");
        }
        if (nascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode estar no futuro.");
        }
    }
}
