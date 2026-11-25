package pagamentos;

import java.util.ArrayList;
import java.util.List;

public class ServicoPagamento {

  public static Pagamento cobrar(Pagador pagador, List<? extends Cobravel> itens) {
    Assinatura assinatura = pagador.getAssinaturas();
    List<String> detalhes = new ArrayList<>();
    double bruto = 0;

    for (Cobravel item : itens) {
      boolean coberto = assinatura != null && assinatura.consumir(item.getTipo());
      if (coberto) {
        detalhes.add("- " + item.getDescricao() + ": R$ 0,00 (plano "
            + assinatura.getPlano().getNome() + ")");
      } else {
        detalhes.add("- " + item.getDescricao() + ": " + Pagamento.formatar(item.getValor()));
        bruto += item.getValor();
      }
    }
    return new Pagamento(pagador, detalhes, bruto, descontoDe(pagador));
  }

  public static Pagamento cobrarMensalidade(Pagador pagador) {
    Assinatura assinatura = pagador.getAssinaturas();
    if (assinatura == null) {
      throw new IllegalStateException(pagador.getNome() + " não possui plano de assinatura.");
    }
    Plano plano = assinatura.getPlano();
    List<String> detalhes = List.of(
        "- Mensalidade " + plano.getNome() + ": " + Pagamento.formatar(plano.getMensalidade()));
    return new Pagamento(pagador, detalhes, plano.getMensalidade(), new SemDesconto());
  }

  private static Desconto descontoDe(Pagador pagador) {
    return pagador.isVip() ? new DescontoVip() : new SemDesconto();
  }
}