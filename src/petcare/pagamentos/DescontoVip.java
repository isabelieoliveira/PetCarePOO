package pagamentos;

public class DescontoVip extends Desconto{
  public DescontoVip(){ super(0.15); }

  @Override
  public String GetRotulo(){ return "Desconto Vip (15%)"; }
  
}
