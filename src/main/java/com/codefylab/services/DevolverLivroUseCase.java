package com.codefylab.services;

import com.codefylab.entities.Livro;
import com.codefylab.entities.Usuario;
import com.codefylab.entities.Aluno;
import com.codefylab.repositories.interfaces.BibliotecaRepository;
import com.codefylab.repositories.interfaces.UsuarioRepository;
import com.codefylab.exceptions.DevolucaoNaoDisponivelException;

public class DevolverLivroUseCase {

    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;

    public DevolverLivroUseCase(BibliotecaRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.bibliotecaRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(int livroId, int usuarioId) throws DevolucaoNaoDisponivelException {
        Livro livro = bibliotecaRepository.findById(livroId);
        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (livro == null || usuario == null) {
            throw new DevolucaoNaoDisponivelException("Livro ou usuário não encontrado.");
        }

        if (livro.isDisponivel()) {
            throw new DevolucaoNaoDisponivelException("Livro já foi devolvido.");
        }

        if (livro.getEmprestadoPara() == null || livro.getEmprestadoPara().getId() != usuarioId) {
            throw new DevolucaoNaoDisponivelException("Este livro não foi emprestado para este usuário.");
        }

        livro.setDisponivel(true);
        livro.setEmprestadoPara(null);

        if (usuario instanceof Aluno aluno) {
            aluno.creditarCreditos();
            usuarioRepository.save(aluno);
        }

        bibliotecaRepository.save(livro);
    }
}