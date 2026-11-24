package petcare;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class AgendamentoConsulta extends Agendamento {

    private static final BigDecimal VALOR_CONSULTA = new BigDecimal("120.00");
    private static final Duration DURACAO_CONSULTA = Duration.ofHours(1);

    public AgendamentoConsulta(Animal animal, String veterinarioId, boolean vip,
                               LocalDateTime inicio, String prontuario) {
        super(animal, veterinarioId, vip, inicio, prontuario);
    }

    @Override
    public Duration getDuracao() { return DURACAO_CONSULTA; }

    @Override
    public BigDecimal getValorBase() { return VALOR_CONSULTA; }

    @Override
    public String getTipo() { return "Consulta"; }
}
