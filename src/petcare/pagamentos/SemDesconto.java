package pagamentos;

public class SemDesconto extends Desconto{
  public SemDesconto(){ super(0); }

  @Override
  public String GetRotulo(){ return "Sem Desconto"; }
}
