package com.fabianocruz.jucesp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Banco implements Serializable {

	private static final long serialVersionUID = -7959181993410639436L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=50)
	private String nome;

	@Column(nullable=false)
	private Integer numero;

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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
}
