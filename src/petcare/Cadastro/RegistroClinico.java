package Cadastro;
import java.util.List;
import exames.Exame;

public class RegistroClinico {
  private final Animal animal;
  private final Proprietario proprietario;
  private final List<Exame> exames;
  private final List<String> anotacoes;
  public RegistroClinico(Animal animal,Proprietario proprietario, List<Exame> exames, List<String> anotacoes) {
    this.animal = animal;
    this.proprietario = proprietario;
    this.exames = List.copyOf(exames);
    this.anotacoes = List.copyOf(anotacoes);
  }
  public String resumo() {
    StringBuilder texto = new StringBuilder
      ("Registro clinico: " + animal + "\nProprietario: " + proprietario + "\nHistorico inicial: " + animal.getHistorico() + "\nAnotações, visitas e tratamentos:\n");
    anotacoes.forEach(a -> texto.append(a).append("\n"));
    texto.append("Exames:\n");
    exames.forEach(e -> texto.append(e).append(" | Resultado: ").append(e.getResultado()).append("\n"));
    return texto.toString();
  }
}
