public Animal cadastrarAnimal(
    String proprietarioId,
    String nome,
    String especie,
    String raca,
    Localdate nascimento,
    String historico) throws IOException{
        buscarProprietario(proprietarioId);
       Animal novo = new Animal(
        UUID.randomUUID().toString(),
        proprietarioId,
        nome,
        especie,
        raca,
        nascimento,
        historico
       );
       arquivos.salvarAnimal(novo);
       animais.put(novo.getId(), novo);

       return novo;
    }
