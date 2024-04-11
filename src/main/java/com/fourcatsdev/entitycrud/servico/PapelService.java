package com.fourcatsdev.entitycrud.servico;

import com.fourcatsdev.entitycrud.modelo.Papel;

import java.util.List;

public interface PapelService {
    public Papel buscarPapelPorId(Long id);
    public Papel buscarPapel(String papel);
    public List<Papel> listarPapel();
}
