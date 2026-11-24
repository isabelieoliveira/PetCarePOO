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

      public double getValor() {
        return VALOR_EXAME;
      }
  public void registrarColeta(){
    if (status != StatusExame.SOLICITADO){
      throw new IllegalStateException("A coleta só pode ser registrada em exames solicitados.");
  }
    status = StatusExame.COLETADO;
  }
public void registrarResultado(String textoResultado, String nomeProprietario, Notificador notificador) {          
  if (status != StatusExame.COLETADO) {
            throw new IllegalStateException("O resultado só pode ser lançado após a coleta.");
  }
            if (textoResultado == null || textoResultado.isBlank()) {
              throw new IllegalArgumentException("O resultado não pode ficar vazio.");            }
           this.resultado = textoResultado.trim();
        this.status = StatusExame.RESULTADO_DISPONIVEL;
        if (notificador != null) {
            notificador.notificar(nomeProprietario,
                "O resultado do exame \"" + tipo + "\" (animal " + animalId + ") já está disponível.");
        }
  }
              

    
            
  
      

  
  
    
