package com.fourcatsdev.entitycrud.repositorio;

import com.fourcatsdev.entitycrud.modelo.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<Papel, Long> {
    Papel findByPapel(String papel);
}
