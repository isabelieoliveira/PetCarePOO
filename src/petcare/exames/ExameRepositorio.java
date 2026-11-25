package petcare.exames;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;  
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExameRepositorio{
  private final Path arquivo;

public ExameRepositorio(String caminho){
        this.arquivo = Path.of(caminho);
}
  public void salvar(List<Exame> exames){
    List<String> linhas = new ArrayList<>();
    for (Exame e : exames){
          linhas.add(String.join(";",
          String.valueOf(e.getId()),
           e.getAnimalId(),
           limpar(e.getTipo()),
                e.getDataSolicitacao().toString(),
                e.getStatus().name(),
                limpar(e.getResultado())));
        }
    try{
      if (arquivo.getParent() != null){
        Files.createDirectories(arquivo.getParent());
      }
      Files.write(arquivo, linhas , StandardCharsets.UTF_8);
    } catch (IOException ex){
      System.out.println("Erro ao salvar exames: " + ex.getMessage());
        }
    }
                                 
                                 


