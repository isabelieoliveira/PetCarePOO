package petcare.vacinacao;

import java.time.LocalDate;

public class Vacina {
  public static final double PRECO = 80.00;

private final String nome;
  private final LocalDate dataAplicacao;
  private final LocalDate validade;

public Vacina(String nome, LocalDate dataAplicacao, LocalDate validade) {
  this.nome = nome;
  this.dataAplicacao = dataAplicacao;
  this.validade = validade;
}

public String getNome() { return nome; }
public LocalDate getDataAplicacao() { return dataAplicacao; }
public LocalDate getValidade() { return validade; }

public boolean venceEm(int dias, LocalDate hoje) {
  return !validade.isBefore(hoje) && !validade.isAfter(hoje.plusDays(dias));
   }
}
