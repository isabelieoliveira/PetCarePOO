import Cadastro.ArquivosRepositorio;
import Cadastro.CadastroConsole;
import Cadastro.CadastroService;
import exames.ExameRepositorio;
import exames.ExameService;
import exames.NotificadorConsole;

import java.io.IOException;

/** Ponto de entrada do sistema PetCare. */
public class Main {

    public static void main(String[] args) {
        try {
            ArquivosRepositorio arquivos = new ArquivosRepositorio("dados");
            CadastroService cadastro = new CadastroService(arquivos);

            ExameRepositorio exameRepositorio = new ExameRepositorio("dados/exames.txt");
            ExameService exames = new ExameService(exameRepositorio, new NotificadorConsole());

            new CadastroConsole(cadastro, exames).iniciar();
        } catch (IOException e) {
            System.err.println("Não foi possível iniciar o sistema: " + e.getMessage());
        }
    }
}
