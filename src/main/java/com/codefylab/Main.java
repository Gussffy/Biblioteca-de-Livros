package com.codefylab;

import com.codefylab.entities.Aluno;
import com.codefylab.entities.Livro;
import com.codefylab.entities.Professor;
import com.codefylab.entities.Usuario;
import com.codefylab.exceptions.DevolucaoNaoDisponivelException;
import com.codefylab.exceptions.EmprestimoNaoDisponivelException;
import com.codefylab.repositories.BibliotecaRepositoryImpl;
import com.codefylab.repositories.UsuarioRepositoryImpl;
import com.codefylab.repositories.interfaces.BibliotecaRepository;
import com.codefylab.repositories.interfaces.UsuarioRepository;
import com.codefylab.services.DevolverLivroUseCase;
import com.codefylab.services.EmprestarLivroUseCase;

public class Main {
    public static void main(String[] args) {

        BibliotecaRepository bibliotecaRepository = new BibliotecaRepositoryImpl();
        UsuarioRepository usuarioRepository = new UsuarioRepositoryImpl();

        EmprestarLivroUseCase emprestarService = new EmprestarLivroUseCase(bibliotecaRepository, usuarioRepository);
        DevolverLivroUseCase devolverService = new DevolverLivroUseCase(bibliotecaRepository, usuarioRepository);

        //Criação dos Livros
        Livro livro1 = new Livro(1, "Java Completo", 1);
        Livro livro2 = new Livro(2, "Estrutura de Dados", 1);
        Livro livro3 = new Livro(3, "Programação Orientada a Objetos", 1);
        Livro livro4 = new Livro(4,"Princípios SOLID",1);



        //Criação de Usuários
        Aluno user1 = new Aluno(1, "Gustavo", 1);
        Professor user2 = new Professor(2, "Fabricio");
        Aluno user3 = new Aluno(3, "Láiza", 1);
        Aluno user4 = new Aluno(4, "Guilherme", 0);

        //Adicionando os livros ao repositório
        bibliotecaRepository.addLivro(livro1);
        bibliotecaRepository.addLivro(livro2);
        bibliotecaRepository.addLivro(livro3);
        bibliotecaRepository.addLivro(livro4);

        //adicionando os usuários ao repositório
        usuarioRepository.addUser(user1);
        usuarioRepository.addUser(user2);
        usuarioRepository.addUser(user3);
        usuarioRepository.addUser(user4);

        System.out.println("\n==== Estado Inicial ====");

        System.out.println("\n---------- Biblioteca ----------");
        listarTodosLivros(bibliotecaRepository);

        System.out.println("\n---------- Usuários ----------");
        listarTodosUsuarios(usuarioRepository);

        System.out.println("\n==== Operações de empréstimos de livros ====\n");
        emprestarLivro(emprestarService, livro1, user1); //Emprestado para Gustavo
        emprestarLivro(emprestarService, livro2, user2); //Professor
        emprestarLivro(emprestarService, livro3, user3); //Emprestado para Láiza
        emprestarLivro(emprestarService, livro4, user4); //Erro: Aluno não possui créditos suficientes.

        System.out.println("\n==== Estado Após Emprestimos ====");

        System.out.println("\n---------- Biblioteca ----------");
        listarTodosLivros(bibliotecaRepository);

        System.out.println("\n---------- Usuários ----------");
        listarTodosUsuarios(usuarioRepository);

        System.out.println("\n==== Operações de devoluções de livros ====\n");

        devolverLivro(devolverService, livro3, user2); //Erro: Este livro não foi emprestado para este usuário.
        devolverLivro(devolverService, livro2, user4); //Erro: Este livro não foi emprestado para este usuário.
        devolverLivro(devolverService, livro1, user1); //Java Completo Devolvido
        devolverLivro(devolverService, livro3, user3); //Programação Orientada a Objetos Devolvido
        devolverLivro(devolverService, livro1, user1); //Erro: Livro já foi devolvido.

        System.out.println("\n==== Estado Após Devoluções ====");

        System.out.println("\n---------- Biblioteca ----------");
        listarTodosLivros(bibliotecaRepository);

        System.out.println("\n---------- Usuários ----------");
        listarTodosUsuarios(usuarioRepository);

    }

    private static void listarTodosLivros(BibliotecaRepository bibliotecaRepository) {
        if (bibliotecaRepository.findAllLivros().isEmpty()) {
            System.out.println("A lista de livros está vazia!");
            return;
        }

        for (Livro livro : bibliotecaRepository.findAllLivros()) {
            System.out.println(" Id: " + livro.getId() +
                    " Título: " + livro.getTitulo() +
                    " Valor: " + livro.getValorCredito() +
                    " Disponível: " + ((livro.isDisponivel())? "SIM" : "NÃO"));
        }
    }

    private static void listarTodosUsuarios(UsuarioRepository usuarioRepository) {
        if (usuarioRepository.findAllUsers().isEmpty()) {
            System.out.println("A lista de usuários está vazia!");
            return;
        }

        for (Usuario usuario : usuarioRepository.findAllUsers()) {
            if (usuario instanceof Aluno aluno) {
                System.out.println(" Id: " + aluno.getId() +
                        " Nome: " + aluno.getNome() + " - Aluno "
                        + " Créditos: " + aluno.getCreditos());
            } else {
                System.out.println(" Id: " + usuario.getId() +
                        " Nome: " + usuario.getNome() + " - Professor");
            }
        }
    }

    private static void emprestarLivro(EmprestarLivroUseCase emprestarService, Livro livro, Usuario usuario) {

        try {

            emprestarService.execute(livro.getId(), usuario.getId());

            if (!livro.isDisponivel() && livro.getEmprestadoPara() != null) {
                System.out.println(" Livro: " + livro.getTitulo() + " emprestado para: " + livro.getEmprestadoPara().getNome());

                if (usuario instanceof Aluno aluno) {
                    System.out.println(" Créditos de " + aluno.getNome() + ": " + aluno.getCreditos());
                }
            }
        } catch (EmprestimoNaoDisponivelException e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }

    private static void devolverLivro(DevolverLivroUseCase devolverService, Livro livro, Usuario usuario) {
        try {

            devolverService.execute(livro.getId(), usuario.getId());

            if (livro.isDisponivel() && livro.getEmprestadoPara() == null) {
                System.out.println(" Livro: " + livro.getTitulo() + " Devolvido ");

                if (usuario instanceof Aluno aluno) {
                    System.out.println(" Créditos de " + aluno.getNome() + ": " + aluno.getCreditos());
                }
            }
        } catch (DevolucaoNaoDisponivelException e) {
            System.out.println(" Erro: " + e.getMessage());
        }
    }
}
