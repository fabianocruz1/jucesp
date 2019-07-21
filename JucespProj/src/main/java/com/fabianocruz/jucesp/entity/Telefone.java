package com.fabianocruz.jucesp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fabianocruz.jucesp.enums.TelefoneTipo;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="telefones")
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1967768306191568421L;

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Funcionario funcionario;

	@Column(nullable = true, length=2)
	private String ddd;

	@Column(nullable = true, length=9)
	private String numero;

	@Column(nullable = true)
	private String complemento;

	@Enumerated(EnumType.STRING)
	private TelefoneTipo tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TelefoneTipo getTipo() {
		return tipo;
	}

	public void setTipo(TelefoneTipo tipo) {
		this.tipo = tipo;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
