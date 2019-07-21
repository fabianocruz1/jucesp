package com.fabianocruz.jucesp.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fabianocruz.jucesp.enums.ContaTipo;

@Entity()
public class DadosBancarios implements Serializable {

	private static final long serialVersionUID = 4967658136433935292L;

	@Id
	@GeneratedValue
	private Long id;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dadosBancarios_id", referencedColumnName = "id")
    private Banco banco;
	
	@Column(nullable=false)
	private Integer agencia;
	
	@Column(nullable=false)
	private Integer conta;
	
	@Column(nullable=false, length=1)
	private String digito;
	
    @Enumerated(EnumType.STRING)
	private ContaTipo tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public String getDigito() {
		return digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	public ContaTipo getTipo() {
		return tipo;
	}

	public void setTipo(ContaTipo tipo) {
		this.tipo = tipo;
	}

}
