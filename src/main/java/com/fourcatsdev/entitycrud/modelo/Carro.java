package com.fourcatsdev.entitycrud.modelo;


import com.fourcatsdev.entitycrud.Enumeration.TipoCarro;
import com.fourcatsdev.entitycrud.Enumeration.TipoCambio;
import com.fourcatsdev.entitycrud.Enumeration.TipoDirecao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Carro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome deve ser informado")
	@Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
	private String nome;
	private int idade;

	private String cor;
	private String placa;
	@Enumerated(EnumType.STRING)
	private TipoCarro tipoCarro;
	private int portas;
	@Enumerated(EnumType.STRING)
	private TipoCambio tipoCambio;
	@Enumerated(EnumType.STRING)
	private TipoDirecao tipoDirecao;
	private int qntsPessoas;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}


	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Enumerated
	public TipoCarro getTipoCarro() {
		return tipoCarro;
	}

	public void setTipoCarro(TipoCarro tipoCarro) {
		this.tipoCarro = tipoCarro;
	}

	public int getPortas() {
		return portas;
	}

	public void setPortas(int portas) {
		this.portas = portas;
	}

	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}


	public TipoDirecao getTipoDirecao() {
		return tipoDirecao;
	}

	public void setTipoDirecao(TipoDirecao tipoDirecao) {
		this.tipoDirecao = tipoDirecao;
	}

	public int getQntsPessoas() {
		return qntsPessoas;
	}

	public void setQntsPessoas(int qntsPessoas) {
		this.qntsPessoas = qntsPessoas;
	}
}
