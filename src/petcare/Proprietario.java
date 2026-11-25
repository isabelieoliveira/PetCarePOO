package petcare;

import java.util.Objects;

public class Proprietario {
  private final String id;
  private final String nome;
  private final String telefone;
  private final String email;
  private final noolean vip;

  public Proprietario(String id, String nome, String telefone, String email) {
    this(id, nome, telefone, email, false);
  }
    public Proprietario(String id, String nome, String telefone, String email, boolean vip) {
    this.vip = vip;
    this.id = Objects.requireNonNull(id, "id não pode ser nulo").trim();
    this.nome = Objects.requireNonNull(nome, "nome não pode ser nulo").trim();
    this.telefone = Objects.requireNonNull(telefone, "telefone não pode ser nulo").trim();
    this.email = Objects.requireNonNull(email, "e-mail não pode ser nulo").trim();

    if (this.id.isBlank()) {
      throw new IllegalArgumentException("id não pode estar vazio");
    }
    if (this.nome.isBlank()) {
      throw new IllegalArgumentException("nome não pode estar vazio");
    }
  }
  public String getId() { return id; }
  public String getNome() { return nome; }
  public String getTelefone() { return telefone; }
  public String getEmail() { return email; }

 public boolean isVip() { return vip; }
  @Override
  public String toString() {
    return nome + (vip ? "[VIP]":  "") + "(tel: " + telefone + ", e-mail: " + email + ")";
  }
}
