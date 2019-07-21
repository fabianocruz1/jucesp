package com.fabianocruz.jucesp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="enderecos")
public class Endereco implements Serializable {

	private static final long serialVersionUID = -5563894405325159469L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length=100)
	private String rua;

	@Column(nullable=false, length=30)
	private String bairro;
	
	@Column(nullable=false, length=30)
	//Usando String para simlificar. 
	//Correto é referenciar tabela de municipio pré carregada com dados do IBGE.
	private String municipio;
	
	@Column(nullable=false, length=2)
	//Usando String para simlificar. 
	//Correto é referenciar tabela de municipio pré carregada com dados do IBGE.
	private String uf;

	@Column(nullable=false)
	private Integer cep;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

}
