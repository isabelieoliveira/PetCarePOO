package petcare.pagamentos;

import java.util.Objects;

public abstract class Plano{
  private final String nome;
  private final double mensalidade;

  protected Plano(String nome, double mensalidade){
    this.nome = nome;

    if (this.nome.isEmpty()){
      throw new IllegalArgumentException("nome não pode ser vazio");
    }
    if (!Double.isFinite(mensalidade) || mensalidade < 0) {
      throw new IllegalArgumentException("mensalidade deve ser um número finito não negativo");
    }
    this.mensalidade = mensalidade;
  }

  public String getNome(){ return nome; }
  public double getMensalidade(){ return mensalidade; }

  public abstract int getConsultasIncluidas();
  public abstract int getVacinasIncluidas();

  @Override
  public String toString(){
    return nome;
  }
  
}
