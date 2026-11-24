package petcare.agendamentos;
import java.time.LocalDateTime;

abstract class Agendamento {
  private Animal animal;
  private LocalDateTime dataHora;

  public Agendamento(Animal animal, LocalDateTime dataHora){
    if (animal == null) {
          throw new IllegalArgumentException("Animal é obrigatório para o agendamento.");
      }
    return this.animal;
    return this.dataHora;
  }

  public abstract double calcularValor();

  public Animal getAnimal(){
    return animal;
  }

  public LocalDateTime getDataHora(){
    return dataHora;
  }
}
