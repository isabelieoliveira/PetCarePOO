package Cadastro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArquivosRepositorio {

    private final Path pasta;

    public ArquivosRepositorio(String pasta) throws IOException {
        this.pasta = Path.of(Objects.requireNonNull(pasta, "pasta"));
        Files.createDirectories(this.pasta);
    }

    /** Lê todos os registros do arquivo. Se o arquivo não existir, devolve lista vazia. */
    public List<String[]> carregar(String arquivo) throws IOException {
        Path caminho = pasta.resolve(arquivo);
        List<String[]> registros = new ArrayList<>();
        if (!Files.exists(caminho)) {
            return registros;
        }
        for (String linha : Files.readAllLines(caminho, StandardCharsets.UTF_8)) {
            if (!linha.isBlank()) {
                registros.add(separar(linha));
            }
        }
        return registros;
    }

    /** Acrescenta um registro ao final do arquivo (cria o arquivo se necessário). */
    public void adicionar(String arquivo, String... campos) throws IOException {
        StringBuilder linha = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            if (i > 0) {
                linha.append(';');
            }
            linha.append(escapar(campos[i]));
        }
        linha.append(System.lineSeparator());

        Files.writeString(
                pasta.resolve(arquivo),
                linha.toString(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    private static String escapar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor
                .replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace("\r", "")
                .replace("\n", "\\n");
    }

    private static String[] separar(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '\\' && i + 1 < linha.length()) {
                char proximo = linha.charAt(++i);
                atual.append(proximo == 'n' ? '\n' : proximo);
            } else if (c == ';') {
                campos.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }
        campos.add(atual.toString());
        return campos.toArray(new String[0]);
    }
}