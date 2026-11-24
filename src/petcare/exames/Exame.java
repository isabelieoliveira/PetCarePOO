package petcare.axmmes;

import java.time.LocalDate;

public class Exame {
  public static final double VALOR_EXAME = 60.00;

private final int id;
  private final String animaLId;
  private final String tipo;
  private final LocalDate dataSolicitacao;
  private StatusExame status;
  private String resultado;

public Exame(int id, String animaLId, String tipo,
            LocalDate dataSolicitacao, StatusExame status, String resultado){
  if (animalId == null || animalIdis.Blank()){
    throw new IllegalArgumentException("O exame precisa estar ligado a um animal.");
  }
  if (tipo == null || tipo.isBlank()){
    throw new IllegalArgumentException("Informe o tipo do exame.");
  }
  this.id = id;
  this.animalId = animalId.trim();
  this.tipo = tipo.trim();
  this.dataSolicitacao = dataSolicitacao;
  this.status = status;
  this.resultado = resultado == null ? "": resultado;
}
  public Exame(int id, String animalId, String tipo) {
    this(id, animalId, tipo, LocalDate.now(), StatusExame.SOLICITADO, "");
}
  
    
