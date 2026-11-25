package agendamentos;
import Cadastro.Animal;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class AgendamentoCirurgia extends Agendamento {

    private static final Duration DURACAO_CIRURGIA = Duration.ofHours(3);

    private final BigDecimal valor; // definido no momento do agendamento

    public AgendamentoCirurgia(Animal animal, String veterinarioId, boolean vip,
                               LocalDateTime inicio, String prontuario, BigDecimal valor) {
        super(animal, veterinarioId, vip, inicio, prontuario);
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor da cirurgia deve ser maior que zero.");
        }
        this.valor = valor;
    }

    @Override
    public Duration getDuracao() { return DURACAO_CIRURGIA; }

    @Override
    public BigDecimal getValorBase() { return valor; }

    @Override
    public String getTipo() { return "Cirurgia"; }
}
