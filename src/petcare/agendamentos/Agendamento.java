package agendamentos;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import Cadastro.Animal;

public abstract class Agendamento implements Comparable<Agendamento> {

    protected static final BigDecimal DESCONTO_VIP = new BigDecimal("0.10"); // 10%
    protected static final List<LocalTime> HORARIOS_EXCLUSIVOS_VIP =
            List.of(LocalTime.of(8, 0), LocalTime.of(17, 0));

    private final String id;
    private final Animal animal;
    private final String veterinarioId;
    private final boolean vip;
    private final LocalDateTime inicio;
    private final String prontuario;

    protected Agendamento(Animal animal, String veterinarioId, boolean vip,
                          LocalDateTime inicio, String prontuario) {
        if (animal == null) {
            throw new IllegalArgumentException("O animal é obrigatório.");
        }
        if (veterinarioId == null || veterinarioId.isBlank()) {
            throw new IllegalArgumentException("O veterinário é obrigatório.");
        }
        if (inicio == null) {
            throw new IllegalArgumentException("A data/hora é obrigatória.");
        }
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("O agendamento não pode ser no passado.");
        }
        if (!vip && HORARIOS_EXCLUSIVOS_VIP.contains(inicio.toLocalTime())) {
            throw new IllegalArgumentException(
                    "Os horários de 8h e 17h são exclusivos para clientes VIP.");
        }

        this.id = UUID.randomUUID().toString();
        this.animal = animal;
        this.veterinarioId = veterinarioId;
        this.vip = vip;
        this.inicio = inicio;
        this.prontuario = prontuario == null ? "" : prontuario.trim();
    }

    // Cada tipo de agendamento define sua duração e seu valor base
    public abstract Duration getDuracao();
    public abstract BigDecimal getValorBase();
    public abstract String getTipo();

    public BigDecimal getValorFinal() {
        if (vip) {
            return getValorBase()
                    .multiply(BigDecimal.ONE.subtract(DESCONTO_VIP))
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return getValorBase().setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDateTime getFim() {
        return inicio.plus(getDuracao());
    }

    // Conflito na agenda do mesmo veterinário
    public boolean conflitaCom(Agendamento outro) {
        if (!this.veterinarioId.equals(outro.veterinarioId)) {
            return false;
        }
        return this.inicio.isBefore(outro.getFim()) && outro.inicio.isBefore(this.getFim());
    }

    // VIP tem prioridade (maior valor = mais prioritário)
    public int getPrioridade() {
        return vip ? 1 : 0;
    }

    @Override
    public int compareTo(Agendamento outro) {
        int porPrioridade = Integer.compare(outro.getPrioridade(), this.getPrioridade());
        return porPrioridade != 0 ? porPrioridade : this.inicio.compareTo(outro.inicio);
    }

    public String getId() { return id; }
    public Animal getAnimal() { return animal; }
    public String getVeterinarioId() { return veterinarioId; }
    public boolean isVip() { return vip; }
    public LocalDateTime getInicio() { return inicio; }
    public String getProntuario() { return prontuario; }

    @Override
    public String toString() {
        return getTipo() + " - " + animal.getNome() + " | " + inicio + " até " + getFim()
                + " | R$ " + getValorFinal() + (vip ? " (VIP)" : "");
    }
}
