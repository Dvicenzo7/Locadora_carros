package com.fourcatsdev.entitycrud.repositorio;

import java.util.List;

import com.fourcatsdev.entitycrud.modelo.Carro;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CarroRepositorio extends JpaRepository<Carro, Long> {
	List<Carro> findByNomeContainingIgnoreCase(String nome);
}
