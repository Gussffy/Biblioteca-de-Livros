package com.codefylab.repositories.interfaces;

import com.codefylab.entities.Livro;
import java.util.List;

public interface BibliotecaRepository {

    Livro findById(int id);
    void save(Livro livro);
    List<Livro> findAllLivros();
}