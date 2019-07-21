package com.fabianocruz.jucesp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabianocruz.jucesp.entity.Protocolo;
import com.fabianocruz.jucesp.enums.AcaoProtocolo;
import com.fabianocruz.jucesp.enums.StatusProtocolo;
import com.fabianocruz.jucesp.repository.ProtocoloRepository;
import com.fabianocruz.jucesp.service.FuncionarioService;
import com.fabianocruz.jucesp.service.ProtocoloService;

@Service
public class ProtocoloServiceImpl implements ProtocoloService {

	@Autowired
	ProtocoloRepository repo;

	@Autowired
	FuncionarioService funcionarioService;
	
	public Protocolo createProtocoloNewFuncionario(String funcionario) {
		return createProtocolo(funcionario, AcaoProtocolo.Incluir);
	}

	@Override
	public Protocolo createProtocoloUpdateFuncionario(String funcionario) {
		return createProtocolo(funcionario, AcaoProtocolo.Alterar);
	}

	@Override
	public Protocolo createProtocoloRemoveFuncionario(String funcionario) {
		return createProtocolo(funcionario, AcaoProtocolo.Remover);
	}

	private Protocolo createProtocolo(String funcionario, AcaoProtocolo acao) {
		Protocolo protocolo = new Protocolo();
		protocolo.setFuncionarioJson(funcionario);
		protocolo.setAcao(acao);
		protocolo.setStatus(StatusProtocolo.Em_Analise);
		
		repo.save(protocolo);
		
		return protocolo;
	}

	@Override
	public List<Protocolo> findAll() {
		List<Protocolo> list = new ArrayList<Protocolo>();
		Iterable<Protocolo> findAll = repo.findAll();
		findAll.forEach(f -> list.add(f));
		return list;
	}

	@Override
	public Protocolo findById(Long id) {
		return repo.findById(id).orElseThrow();
	}

	@Override
	public List<Protocolo> findByStatus(String status) {
		StatusProtocolo.valueOf(status);
		return repo.findAllByStatus(StatusProtocolo.valueOf(status));
	}

	@Override
	public Protocolo updateStatus(Long protocoloId, StatusProtocolo novoStatus) {
		Protocolo protocolo = repo.findById(protocoloId).orElseThrow();
		protocolo.setStatus(novoStatus);
		return repo.save(protocolo);
	}

	@Override
	public void delete(Protocolo protocolo) {
		repo.delete(protocolo);
	}

}
