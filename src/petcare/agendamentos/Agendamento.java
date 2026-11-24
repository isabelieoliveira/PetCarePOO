package petcare.agendamentos;
import java.time.LocalDateTime;

abstract class Agendamento {
  private Animal animal;
  private LocalDateTime dataHora;

  public Agendamento(Animal animal, LocalDateTime dataHora){
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
