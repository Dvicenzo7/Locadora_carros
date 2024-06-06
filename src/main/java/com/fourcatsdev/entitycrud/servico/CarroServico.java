package com.fourcatsdev.entitycrud.servico;

import java.util.List;
import java.util.Optional;

import com.fourcatsdev.entitycrud.modelo.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourcatsdev.entitycrud.excecao.CarroNotFoundException;

import com.fourcatsdev.entitycrud.repositorio.CarroRepositorio;


@Service
public class CarroServico {
	
	@Autowired
	private CarroRepositorio carroRepositorio;
	
	public Carro criarEstudante(Carro carro) {
		return carroRepositorio.save(carro);
	}
	
	public List<Carro> buscarTodosEstudantes() {
		return carroRepositorio.findAll();
	}
	
	public Carro buscarEstudantePorId(Long id)throws CarroNotFoundException {
		Optional<Carro> opt = carroRepositorio.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new CarroNotFoundException("Carro com id : " + id + " não existe");
		}		
	}	
	
	public void apagarEstudante(Long id) throws CarroNotFoundException {
		Carro carro = buscarEstudantePorId(id);
		carroRepositorio.delete(carro);
	}
	
	public Carro alterarEstudante(Carro carro) {
		return carroRepositorio.save(carro);
	}
	
	public List<Carro> buscarTodosEstudantesPorNome(String nome) {
		return carroRepositorio.findByNomeContainingIgnoreCase(nome);
	}

	public boolean reservarCarro(Long id, int dias) {
		Optional<Carro> optionalCarro = carroRepositorio.findById(id);
		if (optionalCarro.isPresent()) {
			Carro carro = optionalCarro.get();
			if (!carro.isReservado()) {
				carro.setReservado(true);
				carro.setTotalReserva(carro.getPrecoDiaria() * dias);
				carroRepositorio.save(carro);
				return true;
			}
		}
		return false;
	} public boolean reservarCarro(Long id) {
		Optional<Carro> optionalCarro = carroRepositorio.findById(id);
		if (optionalCarro.isPresent()) {
			Carro carro = optionalCarro.get();
				carro.setTotalReserva(carro.getPrecoDiaria());
				carroRepositorio.save(carro);
				return true;

		}
		return false;
	}
}
