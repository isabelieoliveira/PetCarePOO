package petcare.pagamentos;

import java.util.ArrayList;
import java.util.Collections;
import java.itil.List;
import java.util.Locale;

public class Pagamento{
  private final Pagador pagador;
  private final List<String> detalhes;
  private final double valorBruto;
  private final Desconto desconto;
  private final double valorDesconto;
  private final boolean pago;

  public Pagamento(Pagador pagador, List<String> detalhes, double valorBruto, Desconto desconto, double valorDesconto, boolean pago ){
    this.pagador = pagador;
    this.detalhes = new ArrayList<>(detalhes);
    this.valorBruto = valorBruto;
    this.desconto = desconto;
    this.valorDesconto = desconto.calcular(valorBruto);
  }

  public Pagador getPagador(){ return pagador; }

  public List<String> getDetalhes(){ return detalhes; }

  public double getValorBruto(){ return valorBruto; }

  public double getValorDesconto(){ return desconto; }

  public double getValorFinal(){ return valorBruto - valorDesconto; }

  public boolean isPago(){ return pago; }

  public void pagar(){
    if (pago){
      throw new IllegalStateException("Esse pagamento já foi realizado.");
    }
    pago = true;
  }

  public static String formatar(double valor){
    return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f, valor");
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("---").append(pagador.getNome()).append(pagador.isVip()? "(VIP)":"").append("---\n");
    for (String d: detalhes){
      sb.append(d).append("\n");
    }
    sb.append("Subtotal:").append(formatar(valorBruto)).append("\n");
    if (valorDesconto > 0){
      sb.append(desconto.getRotulo()).append(":-").append(formatar(valorDesconto)).append("\n");
    }
    sb.append("Total:").append(formatar(getValorFinal())).append(pago?"[PAGO]":"[PENDENTE]").append("\n");
    return sb.toString();
  }
}
