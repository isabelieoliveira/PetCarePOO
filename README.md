#  PetCare — Sistema para Clínica Veterinária

Sistema em **Java** (Programação Orientada a Objetos), executado no console, para a clínica veterinária **PetCare**, que oferece **consultas, cirurgias, vacinação e exames laboratoriais**. O sistema gerencia agendamentos, cadastro de animais, histórico e saúde. Donos de animais **VIP** recebem descontos e atendimento prioritário.

> **Disciplina:** POO
> **Professor: Samuel

##  Equipe e responsabilidades

| Integrante | Módulo |
|---|---|
| **Isabelle Luiza Rodrigues de Oliveira** | Cadastro de animais e proprietários, histórico de saúde |
| **Laila Lopes Arnaud Sampaio ** | Consultas e cirurgias (agenda, prontuário, prioridade VIP) |
| **Ana Clara Cunha dos Santos** | Controle de exames e laboratório |
| **Ávylla da Silva Veras ** | Pagamentos, descontos para VIPs e planos de assinatura |
| **Bianca Gabriela Martins de Oliveira** | Vacinação, lembretes e certificado de vacinação |

---

##  Requisitos e regras de negócio

### 1. Cadastro de animais e proprietários (Isabelle)
- Registro de animais com informações detalhadas: espécie, raça, idade e histórico de saúde.
- Registro de proprietários, com indicação se é **VIP**.
- Histórico completo de visitas, exames e tratamentos.

### 2. Consultas e cirurgias (Laila)
| Serviço | Duração | Valor |
|---|---|---|
| Consulta | 1 hora | R$ 120,00 |
| Cirurgia | 3 horas | definido no momento do agendamento |

- Prontuário do animal para cada atendimento.
- **Sem conflito** na agenda do veterinário.
- **Atendimento prioritário para animais VIP**, com horários exclusivos às **8h e 17h**.

### 3. Vacinação e lembretes (Bianca)
- Controle do calendário de vacinação, a **R$ 80,00 por vacina**.
- Lista de lembretes das vacinas que **vencem nos próximos 15 dias**.
- Emissão de **certificado de vacinação** atualizado para cada animal.

### 4. Exames e laboratório (Ana Clara)
- Agendamento e acompanhamento de exames laboratoriais, a **R$ 60,00**.
- Status: **solicitado → coletado → resultado disponível**.
- **Notificação ao proprietário** quando o resultado ficar disponível.

### 5. Pagamentos e descontos (Ávylla)
- Controle de pagamentos, com **15% de desconto automático** para clientes VIP.
- Planos de assinatura:

| Plano | Valor | Franquia incluída |
|---|---|---|
| Básico | R$ 90,00/mês | 1 consulta |
| Completo | R$ 180,00/mês | 2 consultas e 1 vacina |

- O que ultrapassar a franquia é cobrado normalmente.

---

## Status da implementação

| Requisito | Status |
|---|---|
| Cadastro de proprietários e animais (espécie, idade, histórico de saúde) |
| Registro clínico com anotações e exames do animal | 
| Histórico de visitas e tratamentos | 
| Marcação de proprietário VIP | A implementar |
| Consultas e cirurgias (agenda, conflito, horários VIP) | 
| Vacinação, lembretes de 15 dias e certificado | 
| Exames: status e notificação ao proprietário (R$ 60,00) | ✅ Implementado |
| Pagamentos com 15% de desconto VIP | 
| Planos de assinatura Básico e Completo |

> Atualize esta tabela conforme cada integrante concluir o seu módulo.

---

##  Estrutura do projeto

```
PetCarePOO/
├── src/petcare/
│   ├── Main.java                 # ponto de entrada
│   ├── CadastroConsole.java      # menu e leitura de dados
│   ├── CadastroService.java      # proprietários, animais e anotações
│   ├── ArquivoRepositorio.java   # leitura e gravação em arquivos
│   ├── Proprietario.java
│   ├── Animal.java              
│   ├── RegistroClinico.java
│   ├── exames/                   
│   │   ├── Exame.java
│   │   ├── StatusExame.java
│   │   ├── Notificador.java          # interface
│   │   ├── NotificadorConsole.java
│   │   ├── ExameRepositorio.java     # leitura e gravação dos exames em CSV
│   │   └── ExameService.java         # regras de exames
│   ├── consultas/               
│   ├── vacinacao/               
│   │   ├── Vacina.java
│   │   └── CalendarioVacinacao.java
│   └── pagamentos/               
├── docs/
│   └── relatorio.pdf
├── README.md
└── .gitignore
```


**Ponto de integração entre módulos:** a informação de proprietário **VIP** é definida no cadastro (Isabelle) e usada pelos horários prioritários (Laila) e pelo desconto de 15% (Ávylla).

---

##  Conceitos de POO aplicados

- **Encapsulamento:** atributos privados, com validação nos construtores e nos serviços
- **Interface e polimorfismo:** `Notificador` e `NotificadorConsole`
- **Enum com comportamento:** `StatusExame`
- **Composição:** `RegistroClinico` reúne animal, proprietário, exames e anotações
- **Separação de responsabilidades:** modelo, serviços, persistência e interface


