package petcare;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class CadastroService {

    private final Arquivos arquivos;
    private final Map<String, Animal> animais;
    private final Map<String, Proprietario> proprietarios;

    public CadastroService(
        Arquivos arquivos,
        Map<String, Animal> animais,
        Map<String, Proprietario> proprietarios) {

        this.arquivos = Objects.requireNonNull(arquivos, "arquivos");
        this.animais = Objetcs.requireNonNull(animais, "animais");
        this.proprietarios = Objects.requireNonNull(proprietarios, "proprietarios");
    }
public Animal cadastrarAnimal(
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
