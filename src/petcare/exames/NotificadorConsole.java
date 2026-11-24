package petcare.exames;

public class NotificadorConsole implements Notificador{
  @Override 
  public void notificar(String nomeProprietario, String mensagem){
    System.out.println("[NOTIFICAÇÃO para " + nomeProprietario + "] " + mensagem);

      }
}
    
