public abstract class Plano{
  private final String nome;
  private final double mensalidade;

  protected Plano(Strng nome, double mensalidade){
    this.nome = nome;
    this.mensalidade = mensalidade;
  }

  public String getNome(){ return nome; }
  public double getMensalidade(){ return mensalidade; }

  @Override
  public String toSring(){
    return nome;
  }
  
}
