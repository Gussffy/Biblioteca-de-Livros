# Sistema de Biblioteca

Este projeto simula o funcionamento básico de uma biblioteca, onde alunos e professores podem pegar emprestado e devolver livros.

## Requisitos
- Java JDK 11 ou superior

## Estrutura do projeto 

src/main/java/com/codefylab/
├── entities/
│   ├── Aluno.java
│   ├── Livro.java
│   ├── Professor.java
│   └── Usuario.java
├── exceptions/
│   ├── DevolucaoNaoDisponivelException.java
│   └── EmprestimoNaoDisponivelException.java
├── repositories/
│   ├── interfaces/
│   │   ├── BibliotecaRepository.java
│   │   └── UsuarioRepository.java
│   └── implementations/
│       ├── BibliotecaRepositoryImpl.java
│       └── UsuarioRepositoryImpl.java
├── services/
|   ├── DevolverLivroUseCase.java
|   └── EmprestarLivroUseCase.java
└── Main.Java 

## Como Executar
1. Clone o repositório:
- bash
git clone https://github.com/Gussffy/Biblioteca-de-Livros.git

2. Execute o arquivo Main.Java
- Ao executar os testes e resultados irão aparecer no console
- Para mais informações sobre os testes checar os comentários no código do Main.Java 


