package petcare;

public class Proprietario {
  private final String id;
  private final String nome;
  private final String telefone;
  private final String email;

  public Proprietario(Striing id,String nome, String telefone, String email) {
    this.id = id;
    this.nome = nome;
    this.telefone = telefone;
    this.email = email;
  }
  public String getId() { return id; }
  public String getNome() { return nome; }
  public String getTelefone() { return telefone; }
  public String getEmail() { return email; }

  @Override
  public String toString() {
    return nome + " (tel: " + telefone + ", e-mail: " + email + ")";
  }
}
