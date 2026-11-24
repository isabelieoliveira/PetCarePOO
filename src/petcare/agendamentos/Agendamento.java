import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public abstract class Agendamento {

    /** Horários exclusivos para animais VIP. */
    public static final LocalTime HORARIO_VIP_MANHA = LocalTime.of(8, 0);
    public static final LocalTime HORARIO_VIP_TARDE = LocalTime.of(17, 0);

    private final Animal animal;
    private final String veterinario;
    private final LocalDateTime inicio;

    protected Agendamento(Animal animal, String veterinario, LocalDateTime inicio) {
        if (animal == null) throw new IllegalArgumentException("Animal é obrigatório.");
        if (veterinario == null || veterinario.isBlank())
            throw new IllegalArgumentException("Veterinário é obrigatório.");
        if (inicio == null) throw new IllegalArgumentException("Data/hora é obrigatória.");

        this.animal = animal;
        this.veterinario = veterinario;
        this.inicio = inicio;

        validarHorarioExclusivo();
    }

    // ---------- Métodos abstratos (cada tipo define o seu) ----------

    /** Duração do atendimento. */
    public abstract Duration getDuracao();

    /** Valor antes de qualquer desconto. */
    public abstract double getValorBase();

    /** Percentual de desconto concedido a animais VIP (0.10 = 10%). */
    protected abstract double getPercentualDescontoVip();

    /** Descrição do tipo de agendamento (ex.: "Consulta"). */
    public abstract String getTipo();

    // ---------- Regras comuns ----------

    public LocalDateTime getFim() {
        return inicio.plus(getDuracao());
    }

    /** Valor final: aplica o desconto somente se o animal for VIP. */
    public double getValorFinal() {
        double valor = getValorBase();
        if (animal.isVip()) {
            valor -= valor * getPercentualDescontoVip();
        }
        return valor;
    }

    /** Prioridade de atendimento: VIP = 1 (maior), comum = 2. */
    public int getPrioridade() {
        return animal.isVip() ? 1 : 2;
    }

    /** Verifica se o horário de início é um dos horários exclusivos de VIP. */
    public boolean isHorarioExclusivoVip() {
        LocalTime hora = inicio.toLocalTime();
        return hora.equals(HORARIO_VIP_MANHA) || hora.equals(HORARIO_VIP_TARDE);
    }

    /** Somente animais VIP podem agendar nos horários exclusivos (8h e 17h). */
    private void validarHorarioExclusivo() {
        if (isHorarioExclusivoVip() && !animal.isVip()) {
            throw new IllegalStateException(
                "Os horários de 8h e 17h são exclusivos para animais VIP.");
        }
    }

    /** Dois agendamentos conflitam se o mesmo veterinário tem períodos sobrepostos. */
    public boolean conflitaCom(Agendamento outro) {
        if (!this.veterinario.equalsIgnoreCase(outro.veterinario)) return false;
        return this.inicio.isBefore(outro.getFim()) && outro.inicio.isBefore(this.getFim());
    }

    /** Confere se este agendamento pode entrar na agenda existente. */
    public void validarConflito(List<Agendamento> agenda) {
        for (Agendamento existente : agenda) {
            if (this.conflitaCom(existente)) {
                throw new IllegalStateException(
                    "Conflito na agenda do veterinário " + veterinario + " com " +
                    existente.getTipo() + " de " + existente.inicio + " a " + existente.getFim());
            }
        }
    }

    // ---------- Getters ----------

    public Animal getAnimal() { return animal; }
    public String getVeterinario() { return veterinario; }
    public LocalDateTime getInicio() { return inicio; }

    @Override
    public String toString() {
        return String.format("%s | Animal: %s%s | Vet: %s | %s -> %s | Valor: R$ %.2f",
            getTipo(), animal.getNome(), animal.isVip() ? " (VIP)" : "",
            veterinario, inicio, getFim(), getValorFinal());
    }
}
