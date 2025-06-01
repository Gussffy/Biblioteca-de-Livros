package com.codefylab.repositories;

import com.codefylab.entities.Usuario;
import com.codefylab.repositories.interfaces.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();

    @Override
    public Usuario findById(int id) {
        Optional<Usuario> usuario = usuarios.stream().filter(u -> u.getId() == id).findFirst();
        return usuario.orElse(null);
    }

    @Override
    public void save(Usuario usuario) {
        usuarios.removeIf(u -> u.getId() == usuario.getId());
        usuarios.add(usuario);
    }

    @Override
    public List<Usuario> findAllUsers() {
        return new ArrayList<>(usuarios);
    }

}