import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Consulta: R$ 120,00, duração de 1 hora.
 * Benefícios VIP: 10% de desconto, prioridade e horários exclusivos (8h e 17h).
 */
public class AgendamentoConsulta extends Agendamento {

    public static final double VALOR_CONSULTA = 120.00;
    public static final Duration DURACAO_CONSULTA = Duration.ofHours(1);
    public static final double DESCONTO_VIP_CONSULTA = 0.10; // ajuste conforme a regra da clínica

    public AgendamentoConsulta(Animal animal, String veterinario, LocalDateTime inicio) {
        super(animal, veterinario, inicio);
    }

    @Override
    public Duration getDuracao() { return DURACAO_CONSULTA; }

    @Override
    public double getValorBase() { return VALOR_CONSULTA; }

    @Override
    protected double getPercentualDescontoVip() { return DESCONTO_VIP_CONSULTA; }

    @Override
    public String getTipo() { return "Consulta"; }
}
