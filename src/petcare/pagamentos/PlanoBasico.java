public class PlanoBasico extends Plano{
  public PlanoBasico(){ super("Basico", 90.00); }

  @Override 
  public int getConsultasIncluidas(){ return 1; }

  @Override 
  public int getVacinasIncluidas(){ return 0; }
}
