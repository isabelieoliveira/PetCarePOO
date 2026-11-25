package exames;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExameRepositorio {
    private final Path arquivo;

    public ExameRepositorio(String caminho) {
        this.arquivo = Path.of(caminho);
    }

    public void salvar(List<Exame> exames) {
        List<String> linhas = new ArrayList<>();
        for (Exame e : exames) {
            linhas.add(String.join(";",
                String.valueOf(e.getId()),
                e.getAnimalId(),
                limpar(e.getTipo()),
                e.getDataSolicitacao().toString(),
                e.getStatus().name(),
                limpar(e.getResultado())));
        }
        try {
            if (arquivo.getParent() != null) {
                Files.createDirectories(arquivo.getParent());
            }
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("Erro ao salvar exames: " + ex.getMessage());
        }
    }

    public List<Exame> carregar() {
        List<Exame> exames = new ArrayList<>();
        if (!Files.exists(arquivo)) {
            return exames;
        }
        try {
            int numeroLinha = 0;
            for (String linha : Files.readAllLines(arquivo, StandardCharsets.UTF_8)) {
                numeroLinha++;
                if (linha.isBlank()) continue;
                try {
                    String[] c = linha.split(";", -1);
                    exames.add(new Exame(
                        Integer.parseInt(c[0]), c[1], c[2],
                        LocalDate.parse(c[3]), StatusExame.valueOf(c[4]), c[5]));
                } catch (RuntimeException ex) {
                    System.out.println("Linha " + numeroLinha + " ignorada (inválida): " + linha);
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao ler exames: " + ex.getMessage());
        }
        return exames;
    }

    private String limpar(String texto) {
        return texto.replace(";", ",").replace("\n", " ").replace("\r", " ");
    }
}
