package com.fabianocruz.jucesp.repository;

import org.springframework.data.repository.CrudRepository;

import com.fabianocruz.jucesp.entity.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long>{
	Funcionario findByCpf(String cpf);
}
