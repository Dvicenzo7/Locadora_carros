package com.fourcatsdev.entitycrud.security;

import com.fourcatsdev.entitycrud.modelo.Papel;
import com.fourcatsdev.entitycrud.modelo.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DetalheUsuario implements UserDetails {

    private Usuario usuario;

    public DetalheUsuario(Usuario usuario) {
        super();
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Papel> papeis = usuario.getPapeis();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Papel papel: papeis) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(papel.getPapel());
            authorities.add(sga);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return usuario.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return usuario.isAtivo();
    }

    public String getNome() {
        return usuario.getNome();
    }

}
