package com.fabianocruz.jucesp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fabianocruz.jucesp.enums.AcaoProtocolo;
import com.fabianocruz.jucesp.enums.StatusProtocolo;

@Entity(name = "protocolos")
public class Protocolo implements Serializable {

	private static final long serialVersionUID = -5335856224635161346L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Enumerated(EnumType.STRING)
	private AcaoProtocolo acao;

    @Enumerated(EnumType.STRING)
    private StatusProtocolo status;

    @Column(columnDefinition="text")
    private String funcionarioJson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AcaoProtocolo getAcao() {
		return acao;
	}

	public void setAcao(AcaoProtocolo acao) {
		this.acao = acao;
	}

	public StatusProtocolo getStatus() {
		return status;
	}

	public void setStatus(StatusProtocolo status) {
		this.status = status;
	}

	public String getFuncionarioJson() {
		return funcionarioJson;
	}

	public void setFuncionarioJson(String funcionarioJson) {
		this.funcionarioJson = funcionarioJson;
	}
    
}
