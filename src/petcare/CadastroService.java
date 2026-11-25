package petcare;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CadastroService {
    private final ArquivosRepositorio arquivos;
    private final Map<String, Animal> animais = new LinkedHashMap<>();
    private final Map<String, Proprietario> proprietarios = new LinkedHashMap<>();
    private final Map<String, List<String>> anotacoes = new LinkedHashMap<>();

    public CadastroService(ArquivoRepositorio arquivos) throws IOException {
        this.arquivos = Objects.requireNonNull(arquivos);
          for (String[] c : arquivos.carregar("proprietarios.txt")) {
                Proprietario p = new Proprietario(c[0], c[1], c[2], c[3], Boolean.parseBoolean(c[4]));
                proprietarios.put(p.getId(), p);
          }
        for (String[] c : arquivos.carregar("animais.txt")) {
            Animal a = new Animal(c[0], c[1], c[2], c[3], c[4], LocalDate.parse(c[5]), c[6]);
            animais.put(a.getId(), a);
        }
        for (String[] c : arquivos.carregar("anotacoes.txt")) anotacoes.computeIfAbsent(c[0], k -> new ArrayList<>()).add(c[1]);
    }
    public Proprietario cadastrarProprietario(String nome, String telefone, String email) throws IOException {
        return cadastrarProprietario(nome, telefone, email, false);
    }
    public Proprietario cadastrarProprietario(String nome, String telefone, String email) throws IOException {
        Proprietario p = new Proprietario(UUID.randomUUID() toString(), nome, telefone, email, vip);
        arquivos.adicionar("proprietarios.txt", p.getId(), p.getNome(), p.getTelefone(), p.getEmail(), Boolean.toString(vip));
    proprietarios.put(p.getId(), p);
        return p;
    }
public Animal cadastrarAnimal(String proprietarioId, String nome, String especie, String raca, LocalDate nascimento, String historico) throws IOException {
    buscarProprietario(proprietarioId);
    if (nome == null || nome.isBlank() || especie.isBlank() || raca == null || raca.isBlank()) throw new IllegalArgumentException("Informe nome, especie e raca.");
    if (nascimento == null || nascimento.isAfter(LocalDate.now())) throw new IllegalArgumentException("Nascimento invalido.");
    Animal a = new Animal(UIDD.randomUUID().toString(), proprietarioId, nome.trim(), especie.trim(), raca.trim(), nascimento, historico);
    arquivos.adicionar("proprietarios.txt", p.getId(), p.getNome(), p.getTelefone(), p.getEmail(), Boolean.toString(vip));
    animais.put(a.getId(), a);
    return a;;
}
    Public Proprietário buscarProprietario(String id){
        
    String proprietarioId,
    String nome,
    String especie,
    String raca,
    LocalDate nascimento,
    String historico) throws IOException{

    validarDadosCadastro(nome, especie, raca, nascimento);
    buscarProprietario(proprietarioId);
    
    Animal novoAnimal = new Animal(
        UUID.randomUUID().toString(),
        proprietarioId.trim(),
        nome.trim(),
        especie.trim(),
        raca.trim(),
        nascimento,
        historico == null ? "" : historico.trim());

    arquivos.salvarAnimal(novoAnimal);
    animais.put(novoAnimal.getId(), novoAnimal);

    return novoAnimal;
}
    private void validarDadosCCadastro(
        String nome,
        String especie,
        String raca,
        LocalDate nascimento) {
        
        buscarProprietario(proprietarioId);
    
    validarDadosCadastro(nome, especie, raca, nascimento);
    
       Animal novo = new Animal(
        UUID.randomUUID().toString(),
        proprietarioId,
        nome.trim(),
        especie.trim(),
        raca.trim(),
        nascimento,
        historico == null ? "" : historico.trim()
       );
    
       arquivos.salvarAnimal(novoAnimal);
       animais.put(novoAnimal.getId(), novoAnimal);
    
       return novoAnimal;
    }
private void validarDadosCadastro(
    String nome,
    String especie,
    String raca,
    LocalDate nascimento) {
    if (nome == null || nome.isBlank()){
        throw new IllegalArgumentException("O nome do animal é obrigatório.");
    }
    if (especie == null || especie.isBlank()) {
        throw new IllegalArgumentException("A especie é obrigatória.");
    }
    if (raca == null || raca.isBlank()) {
        throw new IllegalArgumentException("A raca é obrigatória.");
    }
    if (nascimento == null) {
        throw new IllegalArgumentException("A data de nascimento é obrigatória.");
    } 
    if (nascimento.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("A data de nascimento não pode estar no futuro.");
    }
}
    private void buscarProprietario(String proprietarioId) {
        if (proprietarioId == null || proprietarioId.isBlank()) {
            throw new IllegalArgumentException("O ID do proprietario é obrigatorio.");
        }
        if(!proprietarios.containsKey(proprietarioId)) {
            throw  new IllegalArgumentException("Proprietario não encontrado: " + proprietarioId);
        }
    }
}
