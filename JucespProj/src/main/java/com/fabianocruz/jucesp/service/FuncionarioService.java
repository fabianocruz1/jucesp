package com.fabianocruz.jucesp.service;

import java.util.List;

import com.fabianocruz.jucesp.entity.Funcionario;

public interface FuncionarioService {
	List<Funcionario> findAll();
	Funcionario findById(Long id);
	Funcionario findByCpf(String cpf);
	Funcionario create(Funcionario funcionario) throws Exception;
	Funcionario update(Funcionario funcionario) throws Exception;
	void delete(Funcionario funcionario2);
}
