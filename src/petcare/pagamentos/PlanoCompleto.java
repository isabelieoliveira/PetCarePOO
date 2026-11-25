package petcare.pagamentos;

public class PlanoCompleto extends Plano{
  public PlanoCompleto(){ super("Completo", 180.00); }

  @Override 
  public int getConsultasIncluidas(){ return 2; }

  @Override
  public int getVacinasIncluidas(){ return 1; }
  
}
