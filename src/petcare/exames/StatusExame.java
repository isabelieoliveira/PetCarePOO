package petcare.exames;

public enum StatusExame{
  SOLICITADO ("Solicitado"),
  COLETADO("Coletado"),
  RESUlTADO_DISPONIVEL("Resultado disponível");

  private final String descricao;

  StatusExame(String descricao){
    this.descricao = descricao;
  }
  public String getDescricao(){
    return descricao;
  }

  public StatusExame proximo(){
    StatusExame[] todos = values();
    int prox = ordinal() +1;
    return prox <todos.length ? todos[prox] : null;
  }
}
  
