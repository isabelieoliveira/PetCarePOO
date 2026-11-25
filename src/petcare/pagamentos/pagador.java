package petcare.pagamentos;

public interface pagador{
  String getNome();

  boolean isVip();

  Assinatura getAssinaturas();
  
}
