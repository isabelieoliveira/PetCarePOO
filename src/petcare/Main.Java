backage petcare;

import petcare.exames.NotificadorConsole;

import java.io.IOException;

public class Main{
public static void main(String[] args){
try {
ArquivoRepositorio arquivos = new ArquivoRepositorio("dados");
CadastroService cadastro = new CadastroService(arquivos);
ExameService exames = new ExameService(arquivos, cadastro, new NotificadorConsole());
new CadastroConsole(cadastro, exames)).iniciar();
}catch(IOException e) {
System.err.println("Não foi possível iniciar o sistema: " + e.getMessage());
}
}
}
