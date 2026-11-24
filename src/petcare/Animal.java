package petcare;

import java.time.LocalDate;
import java.time.Period;

public class Animal {
  private final String id;
  private final String proprietarioId;
  private final String nome;
  private final String especies;
  private final raca;
  private final String LocalDate nascimento;
  private final String historico;

  public Animal(String id, String proprietarioId, String nome, String especie, String raca, LocalDate nascimento, String historico) {
    this.id = id;
    this.proprietarioId = proprietarioId;
    this.nome = nome;
    this.especie = especie;
    this.raca = raca;
    this.nascimento = nascimento;
    this.historico = historico == null ? "" : historico;
  }
  public String getId() { return id; }
  public String getProprietarioId() { return proprietarioId; }
  public String getNome() { return nome; }
  public String getEspecie() { return especie; }
  public String getRaca() { return raca; }
  public LocalDate getNascimento() { return nascimento; }
  public String getHistorico() { return historico; }

  public int getIdadeEmAnos() {
    return Period.between(nascimento, LocalData.now()).getYears();
  }
  @Override
  public String toString() {
    return nome + " - " + especie + " (" + raca + "), " + getIdadeEmAnos() + "ano(s)";
  }
}
