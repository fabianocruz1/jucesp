package com.fabianocruz.jucesp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fabianocruz.jucesp.enums.Cargo;
import com.fabianocruz.jucesp.enums.EstadoCivil;
import com.fabianocruz.jucesp.enums.StatusCadastro;

@Entity(name = "funcionarios")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = -5335856224635161346L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message="Nome não pode ser nulo")
	@NotBlank(message="Nome não pode ser nulo")
	@Size(min=2, max=100, message="Tamanho deve ser entre 2 e 100 caracteres")
	@Column(nullable = true, length=100)
	private String nome;
	
	@NotNull(message="Cpf não pode ser nulo")
	@Size(min=11, max=11, message="Cpf deve ter 11 dígitos")
	@Column(nullable = false, length=11, unique=true)
	private String cpf;
	
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy="funcionario")
    private List<Telefone> telefones;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dadosBancarios_id", referencedColumnName = "id")
    private DadosBancarios dadosBancarios;
	
    @Enumerated(EnumType.STRING)
    //Para simplificar vou usar enum com alguns cargos.
    //Deve referenciar tabela de cargos
    private Cargo cargo;
	
    @Column(nullable=false)
    //salario em centavos. Ex: R$2.000,00 = 200000
    private Integer salario;

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

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public DadosBancarios getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancarios dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Integer getSalario() {
		return salario;
	}

	public void setSalario(Integer salario) {
		this.salario = salario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
}
