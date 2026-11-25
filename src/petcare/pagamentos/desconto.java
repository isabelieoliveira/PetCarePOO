package pagamentos;

public abstract class Desconto{
  private final double percentual;

  protected Desconto(double percentual){
    this.percentual = percentual;
  }

  public double calcular(double valor){
    return Math.round(valor*percentual*100)/100.0;
  }

  public abstract String GetRotulo();
}
