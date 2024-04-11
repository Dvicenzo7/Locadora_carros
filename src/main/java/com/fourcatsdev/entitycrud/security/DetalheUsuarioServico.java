package com.fourcatsdev.entitycrud.security;

import com.fourcatsdev.entitycrud.modelo.Usuario;
import com.fourcatsdev.entitycrud.repositorio.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public DetalheUsuarioServico(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByLogin(username);

        if(usuario != null && usuario.isAtivo()) {
            DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
            return detalheUsuario;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
