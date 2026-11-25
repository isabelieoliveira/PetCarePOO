package exames;

import java.util.ArrayList;
import java.util.List;

public class ExameService{
  private final List<Exame> exames;
  private final ExameRepositorio repositorio;
  private final Notificador notificador;

public ExameService(ExameRepositorio repositorio, Notificador notificador){
  this.repositorio = repositorio;
  this.notificador = notificador;
  this.exames = repositorio.carregar();
}
  public Exame solicitar(String animalId, String tipo){
    Exame novo = new Exame(proximoId(), animalId, tipo);
    exames.add(novo);
    repositorio.salvar(exames);
    return novo;
  }
  public void registrarColeta(int id){
    buscarPorId(id).registrarColeta();
        repositorio.salvar(exames);
  }
  public void lancarResultado(int id, String resultado, String nomeProprietario){
    buscarPorId(id).registrarResultado(resultado, nomeProprietario, notificador);
    repositorio.salvar(exames);
  }
   public List<Exame> listarTodos() {
        return new ArrayList<>(exames);
    }
   public List<Exame> listarPorAnimal(String animalId) {
        List<Exame> resultado = new ArrayList<>();
        for (Exame e : exames) {
            if (e.getAnimalId().equals(animalId)) {
                resultado.add(e);
            }
        }
     return resultado;
   }
  public Exame buscarPorId(int id){
    for (Exame e : exames){
      if(e.getId() ==id ){
        return e;
      }
    }
    throw new IllegalArgumentException("Exame não encontrado.");
    }
private int proximoId(){
  int maior =0;
  for (Exame e : exames){
    if (e.getId() > maior) {
                maior = e.getId();
            }
        }
        return maior + 1;
    }
}
    
