import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Cirurgia: valor definido no agendamento, duração de 3 horas.
 * Benefícios VIP: 15% de desconto, prioridade e horários exclusivos (8h e 17h).
 */
public class AgendamentoCirurgia extends Agendamento {

    public static final Duration DURACAO_CIRURGIA = Duration.ofHours(3);
    public static final double DESCONTO_VIP_CIRURGIA = 0.15; // ajuste conforme a regra da clínica

    private final double valor;
    private final String procedimento;

    public AgendamentoCirurgia(Animal animal, String veterinario, LocalDateTime inicio,
                               String procedimento, double valor) {
        super(animal, veterinario, inicio);
        if (valor <= 0) throw new IllegalArgumentException("O valor da cirurgia deve ser positivo.");
        this.procedimento = procedimento;
        this.valor = valor;
    }

    @Override
    public Duration getDuracao() { return DURACAO_CIRURGIA; }

    @Override
    public double getValorBase() { return valor; }

    @Override
    protected double getPercentualDescontoVip() { return DESCONTO_VIP_CIRURGIA; }

    @Override
    public String getTipo() { return "Cirurgia (" + procedimento + ")"; }

    public String getProcedimento() { return procedimento; }
}
