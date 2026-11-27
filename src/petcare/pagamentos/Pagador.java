package pagamentos;

public interface Pagador{
  String getNome();

  boolean isVip();

  Assinatura getAssinaturas();
  
}
