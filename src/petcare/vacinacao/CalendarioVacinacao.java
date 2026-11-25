package vacinacao;

import Cadastro.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioVacinacao {
  private final Map<String, List<Vacina>> vacinasPorAnimal = new HashMap<>();
  private final Map<String, Animal> animais = new HashMap<>();

public void registrar(Animal animal, Vacina vacina) {
  animais.put(animal.getId(), animal);
  vacinasPorAnimal
    .computeIfAbsent(animal.getId(), id -> new ArrayList<>())
    .add(vacina);
}

public double calcularCusto(Animal animal) {
  List<Vacina> lista = vacinasPorAnimal.getOrDefault(animal.getId(), new ArrayList<>());
  return lista.size() * Vacina.PRECO;
}
  
  public List<String> lembretes(LocalDate hoje) {
    List<String> resultado = new ArrayList<>();
    for (String id: vacinasPorAnimal.keySet()) {
      Animal animal = animais.get(id);
      for (Vacina v : vacinasPorAnimal.get(id)) {
        if (v.venceEm(15, hoje)){
          resultado.add(animal.getNome() + " - " + v.getNome()
                      + " vence em " + v.getValidade());
        }
      }
    }
    return resultado;
  }

public String emitirCertificado(Animal animal) {
  StringBuilder sb = new StringBuilder();
  sb.append("CERTIFICADO DE VACINACAO - PetCare\n");
  sb.append("Animal: ").append(animal.getNome()).append("\n");
  List<Vacina> lista = vacinasPorAnimal.getOrDefault(animal.getId(), new ArrayList<>());
  if (lista.isEmpty()) {
    sb.append("Nenhuma vacina registrada. \n");
  }
  for (Vacina v : lista) {
    sb.append("- ").append(v.getNome())
      .append(" | aplicada em ").append(v.getDataAplicacao())
      .append(" | valida ate ").append(v.getValidade()).append("\n");
  }
  return sb.toString();
}
}
