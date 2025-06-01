package com.codefylab.repositories;

import com.codefylab.entities.Livro;
import com.codefylab.repositories.interfaces.BibliotecaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibliotecaRepositoryImpl implements BibliotecaRepository {
    private final List<Livro> livros = new ArrayList<>();

    @Override
    public Livro findById(int id) {
        Optional<Livro> livro = livros.stream().filter(l -> l.getId() == id).findFirst();
        return livro.orElse(null);
    }

    @Override
    public void save(Livro livro) {
        livros.removeIf(l -> l.getId() == livro.getId());
        livros.add(livro);
    }

    @Override
    public List<Livro> findAllLivros() {
        return new ArrayList<>(livros);
    }

    @Override
    public void addLivro(Livro livro) {
        livros.add(livro);
    }

}