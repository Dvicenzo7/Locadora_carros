package com.fourcatsdev.entitycrud.repositorio;

import com.fourcatsdev.entitycrud.modelo.Carro;
import com.fourcatsdev.entitycrud.modelo.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {

//    List<Faq> findByNomeContainingIgnoreCase(String titulo);
}
