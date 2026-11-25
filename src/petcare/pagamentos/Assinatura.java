package petcare.pagamentos;

public class Assitura{
  private final Plano plano;
  private int consultasUsadas;
  private int vacinasUsadas;

  public Assinatura(Plano plano){
    if(plano == null){
      throw new IllegalArgumentException("O plano não pode ser nulo.");
    }
    this.plano = plano;
  }

  public Plano getPlano(){
    return plano;
  }

  public int consultasRestantes(){
    return plano.getConsultasIncluidas() - consultasUsadas();
  }

  public int vacinasRestantes(){
    return plano.getVacinasIncluidas() - vacinasUsadas();
  }

}
