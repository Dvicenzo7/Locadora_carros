package com.fourcatsdev.entitycrud.servico;

import com.fourcatsdev.entitycrud.modelo.Papel;
import com.fourcatsdev.entitycrud.modelo.Usuario;
import com.fourcatsdev.entitycrud.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PapelService papelService;

    @Autowired
    private BCryptPasswordEncoder criptografia;

    @Override
    public void apagarUsuarioPorId(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new IllegalArgumentException("Usuário com id : " + id + " não existe");
        }
    }

    @Override
    public Usuario buscarUsuarioPorLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        return usuario;
    }

    @Override
    public Usuario gravarUsuario(Usuario usuario) {
        Papel papel = papelService.buscarPapel("USER");
        List<Papel> papeis = new ArrayList<Papel>();
        papeis.add(papel);
        usuario.setPapeis(papeis); // associa o papel de USER ao usuário

        String senhaCriptografia = criptografia.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografia);

        return usuarioRepository.save(usuario);
    }

    @Override
    public void alterarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Override
    public void atribuirPapelParaUsuario(long idUsuario, int[] idsPapeis, boolean isAtivo) {
        List<Papel> papeis = new ArrayList<Papel>();
        for (int i = 0; i < idsPapeis.length; i++) {
            long idPapel = idsPapeis[i];
            Papel papel = papelService.buscarPapelPorId(idPapel);
            papeis.add(papel);
        }
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        usuario.setPapeis(papeis);
        usuario.setAtivo(isAtivo);
        alterarUsuario(usuario);
    }
}
