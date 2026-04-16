package com.example.myapp.services;

import java.util.List;
import java.util.Optional;

import com.example.myapp.domain.Usuario;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    
    Optional<Usuario> getById(Long id);

    Usuario saveUsuario(Usuario usuario);

    void deleteUsuario(Usuario usuario);
}
