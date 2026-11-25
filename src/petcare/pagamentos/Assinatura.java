package petcare.pagamentos;

public class Assitura{
  private final Plano plano;
  private int consultasUsadas;
  private int vacinasUsadas;

  public Assinatura(Plano plano){
    if (plano == null){
      throw new IllegalArgumentException("O plano não pode ser nulo.");
    }
    this.plano = plano;
  }

  public Plano getPlano(){
    return plano;
  }

  public int getConsultasRestantes(){
    return plano.getConsultasIncluidas() - consultasUsadas();
  }

  public int getVacinasRestantes(){
    return plano.getVacinasIncluidas() - vacinasUsadas();
  }

  public boolean consumir(String tipo){
    if (TipoServico.CONSULTA.equals(tipo) && getConsultasRestantes > 0){
      consultasUsadas++;
      return true;
    }
    if (TipoServico.VACINA.equals(tipo) && getVacinasRestantes > 0){
      vacinasUsaadas++;
      return true;
    }
    return false;
  }

  public void novoCiclo(){
    consutasUsadas = 0;
    vacinasUsadas = 0;
  }

}
