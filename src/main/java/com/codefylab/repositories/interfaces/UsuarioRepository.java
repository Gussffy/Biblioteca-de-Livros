package com.codefylab.repositories.interfaces;

import com.codefylab.entities.Usuario;
import java.util.List;

public interface UsuarioRepository {
    Usuario findById(int id);

    void save(Usuario usuario);

    List<Usuario> findAllUsers();

    void addUser(Usuario usuario);

}