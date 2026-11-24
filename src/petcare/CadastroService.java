public Animal cadastrarAnimal(
    String proprietarioId,
    String nome,
    String especie,
    String raca,
    Localdate nascimento,
    String historico) throws IOException{
    
        buscarProprietario(proprietarioId);
    
    validarDadosCadastro(nome, especie, raca, nascimento);
    
       Animal novo = new Animal(
        UUID.randomUUID().toString(),
        proprietarioId,
        nome.trim(),
        especie.trim(),
        raca.trim(),
        nascimento,
        historico == null ? "" : historico.trim()
       );
    
       arquivos.salvarAnimal(novo);
       animais.put(novo.getId(), novo);
    
       return novo;
    }
private void validarDadosCadastro(
    String nome,
    String especie,
    String raca,
    LocalDate nascimento) {
    if (nome == null || nome.isBlank()){
        throw new IllegalArgumentException("O nome do animal é obrigatório.");
    }
    if (especie == null || especie.isBlank()) {
        throw new IllegalArgumentException("A especie é obrigatória.");
    }
    if (raca == null || raca.isBlank()) {
        throw new IllegalArgumentException("A raca é obrigatória.");
    }
    if (nascimento == null) {
        throw new IllegalArgumentException("A data de nascimento é obrigatória.");
    }
    if (nascimento.isAfter(LocalDate.now())) {
        throw new IllegalArguementExcepetion("A data de nascimento não pode estar no futuro.");
    }
}
