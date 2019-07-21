package com.fabianocruz.jucesp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabianocruz.jucesp.entity.Funcionario;
import com.fabianocruz.jucesp.repository.FuncionarioRepository;
import com.fabianocruz.jucesp.repository.TelefoneRepository;
import com.fabianocruz.jucesp.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	@Autowired
	FuncionarioRepository repo;
	
	@Autowired
	TelefoneRepository telRepo;
	
	@Override
	public List<Funcionario> findAll() {
		List<Funcionario> list = new ArrayList<Funcionario>();
		Iterable<Funcionario> findAll = repo.findAll();
		findAll.forEach(f -> list.add(f));
		return list;
	}

	@Override
	public Funcionario findById(Long id) {
		Optional<Funcionario> funcionario = repo.findById(id);
		
		return funcionario.orElseThrow();
	}

	@Override
	public Funcionario findByCpf(String cpf) {
		Funcionario funcionario = repo.findByCpf(cpf);
		
//		if (funcionario == null) 
//			throw new EntityNotFoundException();
		
		return funcionario;
	}

	@Override
	public Funcionario create(Funcionario funcionario) throws Exception {
		Funcionario findByCpf = repo.findByCpf(funcionario.getCpf());
		
		if (findByCpf != null) {
			throw new Exception("Já existe funcionário com esse CPF "+funcionario.getCpf());
		}
		
		funcionario.getTelefones().forEach(t->t.setFuncionario(funcionario));
		Funcionario funcionario2 = repo.save(funcionario);
		
		return funcionario2;
	}

	@Override
	public Funcionario update(Funcionario funcionario) throws Exception {
		Funcionario findByCpf = repo.findByCpf(funcionario.getCpf());
		
		if (findByCpf == null) {
			throw new EntityNotFoundException();
		}
		
		funcionario.setId(findByCpf.getId());
		
		Funcionario funcionario2 = repo.save(funcionario);
		
		return funcionario2;
	}

	@Override
	public void delete(Funcionario funcionario) {
		Funcionario funcionario2 = repo.findById(funcionario.getId()).orElseThrow();
		
		if (funcionario2 == null) {
			throw new EntityNotFoundException();
		}
		
		funcionario.setId(funcionario2.getId());
		
//		funcionario2.getTelefones().forEach(t -> telRepo.delete(t));
//		funcionario2.setTelefones(null);
		repo.delete(funcionario2);
	}


}
