package com.codefylab.services;

import com.codefylab.entities.Professor;
import com.codefylab.entities.Livro;
import com.codefylab.entities.Usuario;
import com.codefylab.entities.Aluno;
import com.codefylab.repositories.interfaces.BibliotecaRepository;
import com.codefylab.repositories.interfaces.UsuarioRepository;
import com.codefylab.exceptions.EmprestimoNaoDisponivelException;

public class EmprestarLivroUseCase {
    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestarLivroUseCase(BibliotecaRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.bibliotecaRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(int livroId, int usuarioId) throws EmprestimoNaoDisponivelException {
        Livro livro = bibliotecaRepository.findById(livroId);
        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (livro == null || usuario == null) {
            throw new EmprestimoNaoDisponivelException("Livro ou usuário não encontrado.");
        }

        if (!livro.isDisponivel()) {
            throw new EmprestimoNaoDisponivelException("Livro não está disponível.");
        }

        if (usuario instanceof Aluno aluno) {
            if (aluno.getCreditos() <= 0) {
                throw new EmprestimoNaoDisponivelException("Aluno não possui créditos suficientes.");
            }

            livro.setDisponivel(false);
            livro.setEmprestadoPara(usuario);
            aluno.debitarCreditos();

            bibliotecaRepository.save(livro);
            usuarioRepository.save(aluno);

        } else if (usuario instanceof Professor) {
            livro.setDisponivel(false);
            livro.setEmprestadoPara(usuario);
            bibliotecaRepository.save(livro);
        }
    }
}