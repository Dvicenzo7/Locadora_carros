package com.fourcatsdev.entitycrud.repositorio;

import com.fourcatsdev.entitycrud.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
}
