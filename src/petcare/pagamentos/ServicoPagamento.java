package petcare.pagamentos;

import java.util.ArrayList;
import java.util.List;

public class ServicoPagamento{
  public static Pagamento cabrar(Pagador pagador, List<? extends Cobrave> itens){
    Assinatura assinatura = pagador.getAssinatura():
    List<String> detalhes = new ArrayList<>();
    double bruto = 0;

    for (cobravel item: itens){
      boolean coberto = assinatura != null && assinatura.consumir(item.getTipo());
      if (coberto){
        detalhes.add("-"+ item.getDescricao() + ":R$ 0,00 (plano" + assinatura.getPlano().getNome()+ ")");
      } else{
        detalhes.add("-" + item.getDescricao() + ":" Pagamento.Formatar(item.getValor()));
        bruto += item.getValor();
      }
    }
    return new Pagamento(pagador, detalhes, bruto, descontoDe(pagador));
    
  }

  public static pagamento cobrarMensalidade (Pagador pagador){
    Assinatura assinatura = pagador.getAssinatura();
    if (assinatura == null){
      throw new IllegalStateExceotion(pagador.getNome() + "Não possui plano de assinatura.");
    }
    Plano plano = assinatura.getPlano();
    List<String> detalhes = List.of("- Mensalidade" + plano.getNome() + "." + Pagamento.formatar(plano.getMensalidade()));
    return new Pagamento(pagador, detalhes, plano.getMensalidade(), new SemDesconto());
  }

  private static Desconto descontoDe(Pagador pagator){
    return pagador.isVip()?new DescontoVip():new SemDesconto();
  }
}
